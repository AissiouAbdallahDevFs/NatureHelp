package com.NatureHelp.api.Model;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Roles {
    

        @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
        private String name;
    
        public Roles() {
        }
        
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }

        public Roles(String name) {
            this.name = name;
        }
    
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
}
