package com.company.demo.screen.document;

import io.jmix.ui.screen.*;
import com.company.demo.entity.Document;

@UiController("Document.browse")
@UiDescriptor("document-browse.xml")
@LookupComponent("documentsTable")
public class DocumentBrowse extends StandardLookup<Document> {
}