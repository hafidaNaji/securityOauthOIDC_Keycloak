package net.naji.inventoryservice.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        // Extraire les autorités du JWT
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet());

        // Retourner un objet Authentication avec les autorités
        return new JwtAuthenticationToken(jwt, authorities, jwt.getClaim("preferred_username"));
    }

    // ajouter cette méthode si elle est utilisée pour extraire des rôles supplémentaires du JWT
    private Collection<GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String,Object> realmAccess;
        Collection<String>roles;
        if (jwt.getClaim("realm_access")==null){
            return Set.of();
        }
        realmAccess =jwt.getClaim("realm_access");
        roles = (Collection<String>) realmAccess.get("roles");
        return roles.stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toSet());
    }
}

