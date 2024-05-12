//package com.praveen.userService.security;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.praveen.userService.models.Role;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//
//@Getter
//@Setter
//@JsonSerialize(as = CustomUserGrantedAuthority.class)
//public class CustomUserGrantedAuthority implements GrantedAuthority {
//    private Role role;
//
//    public CustomUserGrantedAuthority(){
//
//    }
//
//    public CustomUserGrantedAuthority(Role role) {
//        this.role = role;
//    }
//
//    /**
//     * If the <code>GrantedAuthority</code> can be represented as a <code>String</code>
//     * and that <code>String</code> is sufficient in precision to be relied upon for an
//     * access control decision by an {@link AccessDecisionManager} (or delegate), this
//     * method should return such a <code>String</code>.
//     * <p>
//     * If the <code>GrantedAuthority</code> cannot be expressed with sufficient precision
//     * as a <code>String</code>, <code>null</code> should be returned. Returning
//     * <code>null</code> will require an <code>AccessDecisionManager</code> (or delegate)
//     * to specifically support the <code>GrantedAuthority</code> implementation, so
//     * returning <code>null</code> should be avoided unless actually required.
//     *
//     * @return a representation of the granted authority (or <code>null</code> if the
//     * granted authority cannot be expressed as a <code>String</code> with sufficient
//     * precision).
//     */
//    @Override
//    @JsonIgnore
//    public String getAuthority() {
//        return role.getRole();
//    }
//}
