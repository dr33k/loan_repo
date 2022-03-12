package com.agicomputers.LoanAPI.models.entities;

import javax.persistence.*;

@Entity
@Table(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String roleName;

    @Column(columnDefinition = "SET(\"\") NOT NULL")
    private Integer roleAuthority;

    @Column(nullable = false)
    private String roleDescription;
}
