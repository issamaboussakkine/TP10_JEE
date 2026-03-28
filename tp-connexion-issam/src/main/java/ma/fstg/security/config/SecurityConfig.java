package ma.fstg.security.config;

// Issam : Import des annotations et classes Spring nécessaires pour la configuration de sécurité
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// Issam : Cette annotation indique à Spring que cette classe contient des configurations de sécurité
public class SecurityConfig {

    @Bean
    // Issam : Bean qui définit le service de gestion des utilisateurs (stockage en mémoire)
    public UserDetailsService userDetailsService() {

        // Issam : Création d'un utilisateur ADMIN avec identifiants personnalisés
        UserDetails admin = User.withUsername("admin")
                .password("{noop}1234")     // Issam : {noop} = mot de passe en clair (pour développement)
                .roles("ADMIN")              // Issam : Attribution du rôle ADMIN
                .build();

        // Issam : Création d'un utilisateur USER standard (Issam : je garde "user" mais je peux le modifier plus tard)
        UserDetails user = User.withUsername("user")
                .password("{noop}1111")      // Issam : Mot de passe simple pour les tests
                .roles("USER")               // Issam : Attribution du rôle USER uniquement
                .build();

        // Issam : Retourne le gestionnaire d'utilisateurs en mémoire avec les deux comptes créés
        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    // Issam : Bean principal qui définit toutes les règles de sécurité (filtres, autorisations, etc.)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Issam : Configuration des autorisations d'accès selon les URLs
                .authorizeHttpRequests(auth -> auth
                        // Issam : La page de login et les fichiers CSS sont accessibles à tous (sans authentification)
                        .requestMatchers("/login", "/css/**").permitAll()
                        // Issam : Toute URL commençant par /admin/ nécessite le rôle ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Issam : Toute URL commençant par /user/ nécessite le rôle USER ou ADMIN
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        // Issam : Toute autre URL nécessite une authentification
                        .anyRequest().authenticated()
                )

                // Issam : Configuration du formulaire de connexion personnalisé
                .formLogin(form -> form
                        .loginPage("/login")                    // Issam : URL de la page de login personnalisée
                        .loginProcessingUrl("/authenticate")    // Issam : URL qui reçoit les données du formulaire
                        .defaultSuccessUrl("/home", true)       // Issam : Redirection après connexion réussie
                        .failureUrl("/login?error=true")        // Issam : Redirection avec paramètre error en cas d'échec
                        .permitAll()                            // Issam : Rendre la page de login accessible à tous
                )

                // Issam : Configuration de la déconnexion
                .logout(logout -> logout
                        .logoutUrl("/logout")                   // Issam : URL pour se déconnecter
                        .logoutSuccessUrl("/login?logout=true") // Issam : Redirection après déconnexion avec message
                        .permitAll()                            // Issam : Rendre la déconnexion accessible à tous
                );

        // Issam : Retourne l'objet de configuration construit
        return http.build();
    }
}