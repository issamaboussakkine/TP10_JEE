package ma.fstg.security.web;

// Issam : Import de l'annotation Controller pour indiquer que cette classe gère les requêtes HTTP
import org.springframework.stereotype.Controller;
// Issam : Import de l'annotation GetMapping pour gérer les requêtes de type GET
import org.springframework.web.bind.annotation.GetMapping;

@Controller
// Issam : Cette annotation indique à Spring que cette classe est un contrôleur MVC
// Issam : Elle va gérer les requêtes HTTP et retourner des noms de vues (fichiers HTML)
public class AuthController {

    @GetMapping("/login")
    // Issam : Cette méthode gère la requête GET vers l'URL "/login"
    // Issam : Elle affiche la page de connexion personnalisée
    public String login() {
        // Issam : Retourne le nom du fichier HTML "login.html" situé dans resources/templates/
        return "login";
    }

    @GetMapping("/home")
    // Issam : Cette méthode gère la requête GET vers l'URL "/home"
    // Issam : Elle affiche la page d'accueil après connexion réussie
    public String home() {
        // Issam : Retourne le nom du fichier HTML "home.html" situé dans resources/templates/
        return "home";
    }

    @GetMapping("/admin/dashboard")
    // Issam : Cette méthode gère la requête GET vers l'URL "/admin/dashboard"
    // Issam : Issam : Seuls les utilisateurs avec le rôle ADMIN peuvent accéder à cette page
    // Issam : La sécurité est gérée par SecurityConfig.java (hasRole("ADMIN"))
    public String adminDashboard() {
        // Issam : Retourne le fichier HTML "admin-dashboard.html" pour le tableau de bord admin
        return "admin-dashboard";
    }

    @GetMapping("/user/dashboard")
    // Issam : Cette méthode gère la requête GET vers l'URL "/user/dashboard"
    // Issam : Issam : Les utilisateurs avec les rôles USER ou ADMIN peuvent accéder à cette page
    // Issam : La sécurité est gérée par SecurityConfig.java (hasAnyRole("USER", "ADMIN"))
    public String userDashboard() {
        // Issam : Retourne le fichier HTML "user-dashboard.html" pour le tableau de bord utilisateur
        return "user-dashboard";
    }
}