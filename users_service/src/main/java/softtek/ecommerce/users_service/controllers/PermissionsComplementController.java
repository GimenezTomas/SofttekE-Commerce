package softtek.ecommerce.users_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.users_service.entities.Permission;
import softtek.ecommerce.users_service.entities.Role;
import softtek.ecommerce.users_service.entities.dtos.DTOPermission;
import softtek.ecommerce.users_service.exceptions.PermissionInvalidException;
import softtek.ecommerce.users_service.repositories.interfaces.PermissionsRepo;
import softtek.ecommerce.users_service.repositories.interfaces.RolesRepo;
import softtek.ecommerce.users_service.repositories.interfaces.UsersRepo;
import softtek.ecommerce.users_service.services.RoleValidationService;

import javax.transaction.Transactional;
import java.util.Optional;

@RepositoryRestController
public class PermissionsComplementController {
    @Autowired
    RolesRepo rolesRepo;

    @Autowired
    PermissionsRepo permissionsRepo;

    @Autowired
    RoleValidationService roleValidationService;

    @Transactional
    @PutMapping("roles/{idRole}/add_permission")
    @ResponseBody ResponseEntity<Object> addPermission( @PathVariable( value = "idRole" ) String idRole, @RequestBody DTOPermission permissionReq ) throws Exception {
        roleValidationService.validation(permissionReq.getIdCurrentUser(), "MANEJAR_PERMISOS");
        //return new ResponseEntity<Object>("Current user has a problem", HttpStatus.CONFLICT);

        Optional<Permission> permissionOptional = permissionsRepo.findById(permissionReq.getIdPermission());
        Optional<Role> roleOptional = rolesRepo.findById(idRole);

        if ( !permissionOptional.isPresent() )
            return new ResponseEntity<Object>("The permission with id " + permissionReq.getIdPermission() + " does not exists!", HttpStatus.NOT_FOUND);

        if ( !roleOptional.isPresent() )
            return new ResponseEntity<Object>("The role with id " + idRole + " does not exists!", HttpStatus.NOT_FOUND);

        Role role = roleOptional.get();
        Permission permission = permissionOptional.get();

        role.addPermission( permission );
        rolesRepo.save( role );

        return ResponseEntity.ok().build();
    }

    @Transactional
    @PutMapping("roles/{idRole}/remove_permission/{idPermission}")
    @ResponseBody ResponseEntity<Object> removePermission( @PathVariable( value = "idRole" ) String idRole, @PathVariable( value = "idPermission" ) String idPermission, @RequestParam String idCurrentUser ) throws Exception {
        roleValidationService.validation(idCurrentUser, "MANEJAR_PERMISOS");
            //return new ResponseEntity<Object>("Current user has a problem", HttpStatus.CONFLICT);

        Optional<Permission> permissionOptional = permissionsRepo.findById(idPermission);
        Optional<Role> roleOptional = rolesRepo.findById(idRole);
        String requiredPermission = "MANEJAR_PERMISOS";

        if ( !permissionOptional.isPresent() )
            return new ResponseEntity<Object>("The permission with id " + idPermission + " does not exists!", HttpStatus.NOT_FOUND);

        if ( !roleOptional.isPresent() )
            return new ResponseEntity<Object>("The role with id " + idRole + " does not exists!", HttpStatus.NOT_FOUND);

        Role role = roleOptional.get();
        Permission permission = permissionOptional.get();

        role.removePermission( permission );
        rolesRepo.save( role );

        return ResponseEntity.ok().build();
    }

    /*@Transactional
    @PutMapping("/users/{idUser}/updateRole")
    @ResponseBody ResponseEntity<Object> updateRole( @PathVariable( value = "idUser" ) String idUser, @RequestBody Role role ) throws PermissionInvalidException {
        Optional<Permission> permissionOptional = permissionsRepo.findById(permissionReq.getId_permission());
        Optional<Role> roleOptional = rolesRepo.findById(idRole);
        String requiredPermission = "CAMBIAR ROL";

        if ( !permissionOptional.isPresent() )
            return new ResponseEntity<Object>("The permission with id " + permissionReq.getId_permission() + " does not exists!", HttpStatus.NOT_FOUND);

        if ( !roleOptional.isPresent() )
            return new ResponseEntity<Object>("The role with id " + idRole + " does not exists!", HttpStatus.NOT_FOUND);

        Role role = roleOptional.get();
        Permission permission = permissionOptional.get();

        role.addPermission( requiredPermission, permission );
        rolesRepo.save( role );

        return ResponseEntity.ok().build();
    }*/
    //TODO

}
