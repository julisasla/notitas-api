package server;

import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.before;
import static spark.Spark.staticFiles;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import com.auth0.jwt.JWT;

import server.controller.EstudianteController;
import server.controller.TareaController;
import server.security.InvalidTokenException;
import server.security.SecurityService;

public class Router implements TransactionalOps, WithGlobalEntityManager {
	
	EntityManager em = entityManager();
	
	public void configure() {		
		SecurityService securityService = new SecurityService("god");
		
		Spark.before((req, res) -> {
			if (req.requestMethod() != "GET") {
				beginTransaction();
			}
			
			try {
				Long userId = securityService.user(req.headers("Authorization").replace("Bearer ", ""));
				// Hacer algo con el id...
			} catch (InvalidTokenException e) {
				if (req.requestMethod() != "GET") {
					rollbackTransaction();
				}
				
				Spark.halt(401, "<h1><a href='https://www.youtube.com/watch?v=0Jx8Eay5fWQ'>Hack me </a></h1><br/><br/><br/><a href='https://www.youtube.com/watch?v=PtLmEARfStE'> El aleph </a>");
			}
		});

		Spark.get("/", (req, res) -> "Hello world!");
		
		Spark.get("/student", EstudianteController::getAlumno);
		
		Spark.post("/student", EstudianteController::modificarAlumno);
		
		Spark.get("/student/assignments/", TareaController::getTareas);
		
		Spark.after((req, res) -> {
			if(req.requestMethod() != "GET") {
				try {
					commitTransaction();
					em.clear();
				} catch (RollbackException e) {
					rollbackTransaction();
				}				
			}
		});
	}
}