package com.company.demo.screen.actor;

import io.jmix.ui.screen.*;
import com.company.demo.entity.Actor;

@UiController("Actor.browse")
@UiDescriptor("actor-browse.xml")
@LookupComponent("actorsTable")
public class ActorBrowse extends StandardLookup<Actor> {
}