package com.agicomputers.LoanAPI.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name="role")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer roleId;

    @Column(nullable = false)
    private String roleName;

    @Column(nullable = false)
    private String roleAuthorities;

    @Transient
    private String[] roleAuthoritiesArray;


    @Column(nullable = false)
    private String roleDescription;

   public String[] getRoleAuthoritiesArray() {
       //Arrays in the Database are in the format
       //"{"foo","bar"}"

       String withoutBraces = this.roleAuthorities.substring(1,this.roleAuthorities.length()-1);
       this.roleAuthoritiesArray = withoutBraces.split(",");
       return this.roleAuthoritiesArray;
   }

   public void setRoleAuthoritiesArray(String[] roleAuthoritiesArray){
       this.roleAuthoritiesArray = roleAuthoritiesArray;
       //Set roleAuthorities in the format
       //"{"foo","bar"}"

       String auths = "";
       for(int i= 0; i<roleAuthoritiesArray.length;i++){
           auths.concat(
                   roleAuthoritiesArray[i]
                           .concat(
                                   ((i != roleAuthoritiesArray.length-1)?",":"")
                           )
           );
       }

       auths = "{"
               .concat(auths)
               .concat("}");

       this.roleAuthorities = auths;
   }

}
