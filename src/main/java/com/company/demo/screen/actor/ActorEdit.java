package com.company.demo.screen.actor;

import io.jmix.ui.screen.*;
import com.company.demo.entity.Actor;

@UiController("Actor.edit")
@UiDescriptor("actor-edit.xml")
@EditedEntityContainer("actorDc")
public class ActorEdit extends StandardEditor<Actor> {
}