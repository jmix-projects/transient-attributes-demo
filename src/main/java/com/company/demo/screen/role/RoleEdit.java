package com.company.demo.screen.role;

import io.jmix.ui.screen.*;
import com.company.demo.entity.Role;

@UiController("Role.edit")
@UiDescriptor("role-edit.xml")
@EditedEntityContainer("roleDc")
public class RoleEdit extends StandardEditor<Role> {
}