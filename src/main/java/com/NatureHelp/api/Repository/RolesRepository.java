package com.NatureHelp.api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NatureHelp.api.Model.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long> {


}