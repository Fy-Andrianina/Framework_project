import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

import code.generation.Component;
import extra.CreateUser;
import genesis.Constantes;
import genesis.Credentials;
import genesis.CustomChanges;
import genesis.CustomFile;
import genesis.Database;
import genesis.Entity;
import genesis.EntityField;
import genesis.Language;
import handyman.HandyManUtils;

public class App {
        public static void main(String[] args) throws Exception {
                Database[] databases = HandyManUtils.fromJson(Database[].class,
                                HandyManUtils.getFileContent(Constantes.DATABASE_JSON));
                Language[] languages = HandyManUtils.fromJson(Language[].class,
                                HandyManUtils.getFileContent(Constantes.LANGUAGE_JSON));
                Database database;
                Language language;
                String databaseName, user, pwd, host;
                boolean useSSL, allowPublicKeyRetrieval;
                String projectName, entityName;
                Credentials credentials;
                String projectNameTagPath, projectNameTagContent;
                File project, credentialFile;
                String customFilePath, customFileContentOuter;
                Entity[] entities;
                String[] models, controllers, views;
                String modelFile, controllerFile, viewFile, customFile;
                String customFileContent;
                String foreignContext;
                String customChanges, changesFile;
                String navLink, navLinkPath;
                Component compo = new Component();
               try (Scanner scanner = new Scanner(System.in)) {
                        System.out.println("Choose a database engine:");
                        for (int i = 0; i < databases.length; i++) {
                                System.out.println((i + 1) + ") " + databases[i].getNom());
                        }
                        System.out.print("> ");
                        database = databases[scanner.nextInt() - 1];
                        System.out.println("Choose a framework:");
                        for (int i = 0; i < languages.length; i++) {
                                System.out.println((i + 1) + ") " + languages[i].getNom());
                        }
                        System.out.print("> ");
                        language = languages[scanner.nextInt() - 1];
                        System.out.println("Enter your database credentials:");
                        System.out.print("Database name: ");
                        databaseName = scanner.next();
                        System.out.print("Username: ");
                        user = scanner.next();
                        System.out.print("Password: ");
                        pwd = scanner.next();
                        System.out.print("Database host: ");
                        host = scanner.next();
                        System.out.print("Use SSL ?(Y/n): ");
                        useSSL = scanner.next().equalsIgnoreCase("Y");
                        System.out.print("Allow public key retrieval ?(Y/n): ");
                        allowPublicKeyRetrieval = scanner.next().equalsIgnoreCase("Y");
                        System.out.println();
                        System.out.print("Enter your project name: ");
                        projectName = scanner.next();
                        System.out.print("Which entities to import ?(* to select all): ");
                        entityName = scanner.next();
                        credentials = new Credentials(databaseName, user, pwd, host, useSSL,
                                        allowPublicKeyRetrieval);

                        project = new File(projectName);
                        project.mkdir();
                        for (CustomFile c : language.getAdditionnalFiles()) {
                                customFilePath = c.getName();
                                customFilePath = customFilePath.replace("[projectNameMaj]",
                                                HandyManUtils.majStart(projectName));
                                HandyManUtils.createFile(customFilePath);
                                customFileContentOuter = HandyManUtils.getFileContent(Constantes.DATA_PATH +
                                                "/" + c.getContent())
                                                .replace("[projectNameMaj]", HandyManUtils.majStart(projectName));
                                HandyManUtils.overwriteFileContent(customFilePath, customFileContentOuter);
                        }
                        HandyManUtils.extractDir(
                                        Constantes.DATA_PATH + "/" + language.getSkeleton() + "." +
                                                        Constantes.SKELETON_EXTENSION,
                                        project.getPath());
                        credentialFile = new File(project.getPath(), Constantes.CREDENTIAL_FILE);
                        credentialFile.createNewFile();
                        HandyManUtils.overwriteFileContent(credentialFile.getPath(),
                                        HandyManUtils.toJson(credentials));
                        for (String replace : language.getProjectNameTags()) {
                                projectNameTagPath = replace.replace("[projectNameMaj]",
                                                HandyManUtils.majStart(projectName))
                                                .replace("[projectNameMin]", HandyManUtils.minStart(projectName));
                                projectNameTagContent = HandyManUtils.getFileContent(projectNameTagPath).replace(
                                                "[projectNameMaj]",
                                                HandyManUtils.majStart(projectName));
                                projectNameTagContent = projectNameTagContent.replace("[databaseHost]",
                                                credentials.getHost());
                                projectNameTagContent = projectNameTagContent.replace("[databasePort]",
                                                database.getPort());
                                projectNameTagContent = projectNameTagContent.replace("[databaseName]",
                                                credentials.getDatabaseName());
                                projectNameTagContent = projectNameTagContent.replace("[user]",
                                                credentials.getUser());
                                projectNameTagContent = projectNameTagContent.replace("[pwd]",
                                                credentials.getPwd());
                                projectNameTagContent = projectNameTagContent.replace("[projectNameMin]",
                                                HandyManUtils.minStart(projectName));
                                HandyManUtils.overwriteFileContent(projectNameTagPath,
                                                projectNameTagContent);
                        }
                        try (Connection connect = database.getConnexion(credentials)) {
                                CreateUser.create(connect, Constantes.USER_SCRIPT); /////// iciiiii
                                connect.commit();
                                entities = database.getEntities(connect, credentials, entityName);
                                for (int i = 0; i < entities.length; i++) {
                                        entities[i].initialize(connect, credentials, database, language);
                                }
                                models = new String[entities.length];
                                controllers = new String[entities.length];
                                views = new String[entities.length];
                                navLink = "";

                              
                                compo.setProjectName(projectName);
                                compo.getRoutesWithoutParam().add("WelcomeFile");

                                ArrayList<String> links = new ArrayList<>();

                                for (int i = 0; i < models.length; i++) {
                                        models[i] = language.generateModel(entities[i], projectName);
                                        controllers[i] = language.generateController(entities[i], database,
                                                        credentials, projectName);
                                        views[i] = language.generateView(entities[i], projectName);
                                        modelFile = language.getModel().getModelSavePath().replace("[projectNameMaj]",
                                                        HandyManUtils.majStart(projectName));
                                        controllerFile = language.getController().getControllerSavepath().replace(
                                                        "[projectNameMaj]",
                                                        HandyManUtils.majStart(projectName));
                                        viewFile = language.getView().getViewSavePath().replace("[projectNameMaj]",
                                                        HandyManUtils.majStart(projectName));
                                        viewFile = viewFile.replace("[projectNameMin]",
                                                        HandyManUtils.minStart(projectName));
                                        viewFile = viewFile.replace("[classNameMaj]",
                                                        HandyManUtils.majStart(entities[i].getClassName()));
                                        viewFile = viewFile.replace("[classNameMin]",
                                                        HandyManUtils.minStart(entities[i].getClassName()));
                                        modelFile = modelFile.replace("[projectNameMin]",
                                                        HandyManUtils.minStart(projectName));
                                        controllerFile = controllerFile.replace("[projectNameMin]",
                                                        HandyManUtils.minStart(projectName));
                                        modelFile += "/" + HandyManUtils.majStart(entities[i].getClassName()) + "."
                                                        + language.getModel().getModelExtension();
                                        controllerFile += "/" + HandyManUtils.majStart(entities[i].getClassName())
                                                        + language.getController().getControllerNameSuffix() + "."
                                                        + language.getController().getControllerExtension();
                                        viewFile += "/" + language.getView().getViewName() + "." +
                                                        language.getView().getViewExtension();
                                        viewFile = viewFile.replace("[classNameMin]",
                                                        HandyManUtils.minStart(entities[i].getClassName()));
                                        HandyManUtils.createFile(modelFile);
                                        for (CustomFile f : language.getModel().getModelAdditionnalFiles()) {
                                                foreignContext = "";
                                                for (EntityField ef : entities[i].getFields()) {
                                                        if (ef.isForeign()) {
                                                                foreignContext += language.getModel()
                                                                                .getModelForeignContextAttr();
                                                                foreignContext = foreignContext.replace(
                                                                                "[classNameMaj]",
                                                                                HandyManUtils.majStart(ef.getType()));
                                                        }
                                                }
                                                customFile = f.getName().replace("[classNameMaj]",
                                                                HandyManUtils.majStart(entities[i].getClassName()));
                                                customFile = language.getModel().getModelSavePath().replace(
                                                                "[projectNameMaj]",
                                                                HandyManUtils.majStart(projectName)) + "/" + customFile;
                                                customFile = customFile.replace("[projectNameMin]",
                                                                HandyManUtils.minStart(projectName));
                                                customFileContent = HandyManUtils
                                                                .getFileContent(Constantes.DATA_PATH + "/" +
                                                                                f.getContent())
                                                                .replace("[classNameMaj]",
                                                                                HandyManUtils.majStart(entities[i]
                                                                                                .getClassName()));
                                                customFileContent = customFileContent.replace("[projectNameMin]",
                                                                HandyManUtils.minStart(projectName));
                                                customFileContent = customFileContent.replace("[projectNameMaj]",
                                                                HandyManUtils.majStart(projectName));
                                                customFileContent = customFileContent.replace("[databaseHost]",
                                                                credentials.getHost());
                                                customFileContent = customFileContent.replace("[databaseName]",
                                                                credentials.getDatabaseName());
                                                customFileContent = customFileContent.replace("[user]",
                                                                credentials.getUser());
                                                customFileContent = customFileContent.replace("[pwd]",
                                                                credentials.getPwd());
                                                customFileContent = customFileContent.replace(
                                                                "[modelForeignContextAttr]",
                                                                foreignContext);
                                                HandyManUtils.createFile(customFile);
                                                HandyManUtils.overwriteFileContent(customFile, customFileContent);
                                        }
                                        HandyManUtils.createFile(controllerFile);
                                        HandyManUtils.createFile(viewFile);
                                        HandyManUtils.overwriteFileContent(modelFile, models[i]);
                                        HandyManUtils.overwriteFileContent(controllerFile, controllers[i]);
                                        HandyManUtils.overwriteFileContent(viewFile, views[i]);
                                        navLink += language.getNavbarLinks().getLink();
                                        navLink = navLink.replace("[projectNameMaj]",
                                                        HandyManUtils.majStart(projectName));
                                        navLink = navLink.replace("[projectNameMin]",
                                                        HandyManUtils.minStart(projectName));
                                        navLink = navLink.replace("[classNameMin]",
                                                        HandyManUtils.minStart(entities[i].getClassName()));
                                        navLink = navLink.replace("[classNameMaj]",
                                                        HandyManUtils.majStart(entities[i].getClassName()));
                                        navLink = navLink.replace("[classNameformattedMin]",
                                                        HandyManUtils.minStart(HandyManUtils
                                                                        .formatReadable(entities[i].getClassName())));
                                        navLink = navLink.replace("[classNameformattedMaj]",
                                                        HandyManUtils.majStart(HandyManUtils
                                                                        .formatReadable(entities[i].getClassName())));
                                        links.add(HandyManUtils.majStart(HandyManUtils
                                                        .formatReadable(entities[i].getClassName()))); /// iciiii

                                        // Object[] objs = compo.translate_to_Object(
                                        // compo.getFolder("package").get("package"), "biblio.entities");

                                        compo.setData(entities[i]);
                                        compo.setRouteCreate(
                                                        "/" + projectName + "/readById" + compo.getData().getClassName()
                                                                        .toLowerCase() + ".do?id=${id}");
                                        compo.setRouteDelete(
                                                        "/" + projectName + "/delete"
                                                                        + compo.getData().getClassName().toLowerCase()
                                                                        + ".do");
                                        compo.setDataLegendTemplate(Component.getFolder("legendTemplate")
                                                        .get("legendTemplate"));
                                        compo.setTemplatePath(Component.getFolder("editTemplate")
                                                        .get("editTemplate"));
                                        compo.setJSPath(Component.getFolder("editjsTemplate")
                                                        .get("editjsTemplate"));
                                        compo.setDataTemplate(Component.getFolder("dataJSTemplate")
                                                        .get("dataJSTemplate"));
                                        compo.setSubdivisionTemplate(
                                                        Component.getFolder("editsubdivisionTemplate")
                                                                        .get("editsubdivisionTemplate"));
                                        compo.setName("Edit");
                                        compo.setRoute();
                                        compo.createTemplate();
                                        compo.setRouteCreate(
                                                        "/" + projectName + "/tocrud" + compo.getData().getClassName()
                                                                        .toLowerCase() + ".do");
                                        compo.setTemplatePath(Component.getFolder("createTemplate")
                                                        .get("createTemplate"));
                                        compo.setJSPath(Component.getFolder("createjsTemplate")
                                                        .get("createjsTemplate"));
                                        compo.setDataTemplate(Component.getFolder("dataJSTemplate")
                                                        .get("dataJSTemplate"));
                                        compo.setSubdivisionTemplate(
                                                        Component.getFolder("createsubdivisionTemplate")
                                                                        .get("createsubdivisionTemplate"));
                                        compo.setName("Create");
                                        compo.setRoute();
                                        compo.createTemplate();
                                        compo.setRouteCreate(
                                                        "/" + projectName + "/readById" + compo.getData().getClassName()
                                                                        .toLowerCase() + ".do?id=${id}");
                                        compo.setTemplatePath(Component.getFolder("showTemplate")
                                                        .get("showTemplate"));
                                        compo.setJSPath(Component.getFolder("showjsTemplate")
                                                        .get("showjsTemplate"));
                                        compo.setDataTemplate(Component.getFolder("dataJSTemplate")
                                                        .get("dataJSTemplate"));
                                        compo.setSubdivisionTemplate(
                                                        Component.getFolder("showsubdivisionTemplate")
                                                                        .get("showsubdivisionTemplate"));
                                        compo.setName("Show");
                                        compo.setRoute();
                                        compo.createTemplate();

                                        compo.setTemplatePath(Component.getFolder("listTemplate")
                                                        .get("listTemplate"));
                                        compo.setJSPath(Component.getFolder("listjsTemplate")
                                                        .get("listjsTemplate"));
                                        compo.setDataTemplate(Component.getFolder("dataJSTemplate")
                                                        .get("dataJSTemplate"));
                                        compo.setSubdivisionTemplate(
                                                        Component.getFolder("listsubdivisionTemplate")
                                                                        .get("listsubdivisionTemplate"));
                                        compo.setName("List");
                                        compo.setRoute();
                                        compo.createTemplate();
                                        compo.getMainJsContent();

                                }
                                navLinkPath = language.getNavbarLinks().getPath().replace("[projectNameMaj]",
                                                HandyManUtils.majStart(projectName));
                                navLinkPath = navLinkPath.replace("[projectNameMin]",
                                                HandyManUtils.minStart(projectName));
                                System.out.println(navLinkPath);
                                HandyManUtils.overwriteFileContent(navLinkPath,
                                                HandyManUtils.getFileContent(navLinkPath).replace("[navbarLinks]",
                                                                navLink));
                                for (CustomChanges c : language.getCustomChanges()) {
                                        customChanges = "";
                                        for (Entity e : entities) {
                                                customChanges += c.getChanges();
                                                customChanges = customChanges.replace("[classNameMaj]",
                                                                HandyManUtils.majStart(e.getClassName()));
                                                customChanges = customChanges.replace("[classNameMin]",
                                                                HandyManUtils.minStart(e.getClassName()));
                                                customChanges = customChanges.replace("[databaseHost]",
                                                                credentials.getHost());
                                                customChanges = customChanges.replace("[user]", credentials.getUser());
                                                customChanges = customChanges.replace("[databaseName]",
                                                                credentials.getDatabaseName());
                                                customChanges = customChanges.replace("[pwd]", credentials.getPwd());
                                        }
                                        if (c.isWithEndComma() == false) {
                                                customChanges = customChanges.substring(0, customChanges.length() - 1);
                                        }
                                        changesFile = c.getPath().replace("[projectNameMaj]",
                                                        HandyManUtils.majStart(projectName));
                                        HandyManUtils.overwriteFileContent(changesFile,
                                                        HandyManUtils.getFileContent(changesFile).replace(
                                                                        "[customChanges]",
                                                                        customChanges));
                                }
                                ArrayList<String> paths = new ArrayList();
                                for (String s : compo.getRoutesWithoutParam()) {
                                        if (s.contains("List")) {
                                                System.out.println(s); // + ito
                                                paths.add(s);
                                        }
                                }
                                compo.setLinkInMenu(links, paths);
                                compo.GenerateDefaultFile("WelcomeFile");
                                compo.GenerateDefaultFile("Login_auth");
                        }
                }
                Path source = Paths.get( compo.getFolder("template").get("template"));
                Path destination = Paths.get( compo.getFolder("path_view").get("path_view")+"/"+projectName+"_view");
                Path src=  Paths.get( compo.getFolder("src").get("src"));
                try {
                    Component.copyFolder(source, destination);
                    Component.deleteFilesInDirectory(src);
                    System.out.println("Le dossier a été copié avec succès.");
                } catch (IOException e) {
                    System.out.println("Une erreur s'est produite lors de la copie du dossier : " + e.getMessage());
                }
        }
}
