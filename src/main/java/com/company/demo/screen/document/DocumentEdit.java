package com.company.demo.screen.document;

import com.company.demo.entity.Actor;
import com.company.demo.entity.DocActor;
import com.company.demo.entity.Document;
import com.company.demo.entity.Role;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.LoadContext;
import io.jmix.core.SaveContext;
import io.jmix.ui.model.InstanceLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@UiController("Document.edit")
@UiDescriptor("document-edit.xml")
@EditedEntityContainer("documentDc")
public class DocumentEdit extends StandardEditor<Document> {

    @Autowired
    private DataManager dataManager;

    @Install(target = Target.DATA_CONTEXT)
    private Set<Object> commitDelegate(SaveContext saveContext) {
        Document document = getEditedEntity();
        if (saveContext.getEntitiesToSave().contains(document)) {
            addManagerToSaveContext(document, saveContext);
            addSalespersonsToSaveContext(document, saveContext);
        }

        return dataManager.save(saveContext);
    }

    private void addManagerToSaveContext(Document document, SaveContext saveContext) {
        Actor manager = document.getManager();
        if (manager == null) {
            return;
        }
        Role managerRole = dataManager.load(Role.class)
                .query("e.code = ?1", "manager")
                .one();

        DocActor managerDocActor = dataManager.load(DocActor.class)
                .query("e.doc = ?1 and e.role = ?2", document, managerRole)
                .optional()
                .orElseGet(() -> {
                    DocActor docActor = dataManager.create(DocActor.class);
                    docActor.setDoc(document);
                    docActor.setRole(managerRole);
                    return docActor;
                });
        managerDocActor.setActor(manager);

        saveContext.getEntitiesToSave().add(managerDocActor);
    }

    private void addSalespersonsToSaveContext(Document document, SaveContext saveContext) {
        Role salespersonRole = dataManager.load(Role.class)
                .query("e.code = ?1", "salesperson")
                .one();

        List<DocActor> currentSalespersonDocActors = dataManager.load(DocActor.class)
                .query("e.doc = ?1 and e.role = ?2", document, salespersonRole)
                .list();

        // remove excluded
        currentSalespersonDocActors.stream()
                .filter(docActor -> !document.getSalespersons().contains(docActor.getActor()))
                .forEach(docActor -> saveContext.getEntitiesToRemove().add(docActor));

        // save new
        document.getSalespersons().stream()
                .filter(actor -> currentSalespersonDocActors.stream().noneMatch(docActor -> docActor.getActor().equals(actor)))
                .forEach(actor -> {
                    DocActor docActor = dataManager.create(DocActor.class);
                    docActor.setDoc(document);
                    docActor.setRole(salespersonRole);
                    docActor.setActor(actor);
                    saveContext.getEntitiesToSave().add(docActor);
                });
    }

    @Subscribe(id = "documentDl", target = Target.DATA_LOADER)
    public void onDocumentDlPreLoad(InstanceLoader.PreLoadEvent<Document> event) {
        if (event.getLoadContext().getId() == null) {
            event.preventLoad();
        }
    }

    @Install(to = "documentDl", target = Target.DATA_LOADER)
    private Document documentDlLoadDelegate(LoadContext<Document> loadContext) {
        Document document = dataManager.load(loadContext);
        if (document != null) {
            dataManager.load(DocActor.class)
                    .query("e.doc = ?1", document)
                    .fetchPlan(fetchPlanBuilder -> fetchPlanBuilder.addFetchPlan(FetchPlan.BASE).add("actor.name"))
                    .list()
                    .forEach(docActor -> {
                        if (docActor.getRole().getCode().equals("manager")) {
                            document.setManager(docActor.getActor());
                        } else if (docActor.getRole().getCode().equals("salesperson")) {
                            if (document.getSalespersons() == null) {
                                document.setSalespersons(new ArrayList<>());
                            }
                            document.getSalespersons().add(docActor.getActor());
                        }
                    });
        }
        return document;
    }


}