package com.company.demo.screen.role;

import io.jmix.ui.screen.*;
import com.company.demo.entity.Role;

@UiController("Role.browse")
@UiDescriptor("role-browse.xml")
@LookupComponent("rolesTable")
public class RoleBrowse extends StandardLookup<Role> {
}