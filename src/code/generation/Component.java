package code.generation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Stream;

import genesis.Constantes;
import genesis.Entity;
import genesis.EntityField;

public class Component {
    private static ArrayList<String> routesWithParam = new ArrayList<>();

    public static void setRoutesWithParam(ArrayList<String> routesWithParam) {
        Component.routesWithParam = routesWithParam;
    }

    private String routeDelete;

    public String getRouteDelete() {
        return routeDelete;
    }

    public void setRouteDelete(String routeDelete) {
        this.routeDelete = routeDelete;
    }

    private String routeCreate;

    public String getRouteCreate() {
        return routeCreate;
    }

    public void setRouteCreate(String routeCreate) {
        this.routeCreate = routeCreate;
    }

    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public static ArrayList<String> getRoutesWithParam() {
        return routesWithParam;
    }

    private static ArrayList<String> routesWithoutParam = new ArrayList<>();

    public static void setRoutesWithoutParam(ArrayList<String> routesWithoutParam) {
        Component.routesWithoutParam = routesWithoutParam;
    }

    public static ArrayList<String> getRoutesWithoutParam() {
        return routesWithoutParam;
    }

    public String dataLegendTemplate;

    public String getDataLegendTemplate() {
        return dataLegendTemplate;
    }

    public void setDataLegendTemplate(String dataLegendTemplate) {
        this.dataLegendTemplate = dataLegendTemplate;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    private String route;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Entity data;

    public Entity getData() {

        return data;
    }

    public void setData(Entity data) {
        this.data = data;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    private String templatePath;
    private String JSPath;

    public String getJSPath() {
        return JSPath;
    }

    public void setJSPath(String jSPath) {
        JSPath = jSPath;
    }

    private String subdivisionTemplate;

    public String getSubdivisionTemplate() {
        return subdivisionTemplate;
    }

    public void setSubdivisionTemplate(String subdivisionTemplate) {
        this.subdivisionTemplate = subdivisionTemplate;
    }

    private String dataTemplate;

    public String getDataTemplate() {
        return dataTemplate;
    }

    public void setDataTemplate(String dataTemplate) {
        this.dataTemplate = dataTemplate;
    }

    // generate all ligne for each attribute of the object
    public String complete(String path) throws Exception {
        String templateSubdivisions = "";
        try {
            String ligne = this.ReadTemplate(path);

            for (EntityField attriObject : this.getData().getFields()) {

                // legende de tableau et attribut dans data
                if (path.compareToIgnoreCase(
                        this.getFolder("dataJSTemplate").get("dataJSTemplate")) == 0
                        || path.compareToIgnoreCase(
                                this.getFolder("legendTemplate").get("legendTemplate")) == 0) {
                    templateSubdivisions = templateSubdivisions
                            + this.generateSubdivision(attriObject.getType(), attriObject.getName(), ligne)
                            + "\n\t\t";
                } else if (path.compareToIgnoreCase(
                        this.getFolder("editsubdivisionTemplate").get("editsubdivisionTemplate")) == 0
                        && attriObject.isForeign() == true
                        || path.compareToIgnoreCase(
                                this.getFolder("createsubdivisionTemplate").get("createsubdivisionTemplate")) == 0
                                && attriObject.isForeign() == true) {
                    String select = this.ReadTemplate(this.getFolder("select").get("select"));
                    select = select.replace("#dataLabel#", attriObject.getName());
                    templateSubdivisions = templateSubdivisions
                            + select + "\n\t\t";

                } else if (path.compareToIgnoreCase(
                        this.getFolder("data").get("data")) == 0
                        && attriObject.isForeign() == true
                        || path.compareToIgnoreCase(
                                this.getFolder("dataVide").get("dataVide")) == 0
                                && attriObject.isForeign() == true) {
                    String datas = this.generateSubdivision(attriObject.getType(), attriObject.getName() + "s", ligne);
                    datas = datas.replace("projectInfo", "response.data");
                    templateSubdivisions = templateSubdivisions
                            + datas
                            + "\n\t\t";

                    templateSubdivisions = templateSubdivisions
                            + this.generateSubdivision(attriObject.getType(), attriObject.getName(), ligne)
                            + "\n\t\t";

                }

                else if (path.compareToIgnoreCase(
                        this.getFolder("data").get("data")) == 0
                        && attriObject.isForeign() == false
                        || path.compareToIgnoreCase(
                                this.getFolder("dataVide").get("dataVide")) == 0
                                && attriObject.isForeign() == false
                        || path.compareToIgnoreCase(
                                this.getFolder("formdata").get("formdata")) == 0
                                && attriObject.isForeign() == false) {

                    templateSubdivisions = templateSubdivisions
                            + this.generateSubdivision(attriObject.getType(), attriObject.getName(), ligne)
                            + "\n\t\t";

                }

                else if (path.compareToIgnoreCase(
                        this.getFolder("formdata").get("formdata")) == 0
                        && attriObject.isForeign() == true) {
                    String datavalue = this.generateSubdivision2part(attriObject.getType(),
                            attriObject.getName() + ".id",
                            attriObject.getName(),
                            ligne);
                    templateSubdivisions = templateSubdivisions
                            + datavalue
                            + "\n\t\t";
                } else if (path.compareToIgnoreCase(
                        this.getFolder("listsubdivisionTemplate").get("listsubdivisionTemplate")) == 0
                        || path.compareToIgnoreCase(
                                this.getFolder("showsubdivisionTemplate").get("showsubdivisionTemplate")) == 0
                        || path.compareToIgnoreCase(
                                this.getFolder("editsubdivisionTemplate").get("editsubdivisionTemplate")) == 0
                                && attriObject.isPrimary() == false && attriObject.isForeign() == false
                        || path.compareToIgnoreCase(
                                this.getFolder("createsubdivisionTemplate").get("createsubdivisionTemplate")) == 0
                                && attriObject.isPrimary() == false && attriObject.isForeign() == false) {
                    templateSubdivisions = templateSubdivisions
                            + this.generateSubdivision(attriObject.getType(), attriObject.getName(), ligne)
                            + "\n\t\t";

                }

            }

        } catch (Exception e) {
            throw e;
        }
        return templateSubdivisions;

    }

    public String generateJs(String path, String pathsubdivisionJS) throws Exception {
        try {
            File template = new File(path);
            Scanner scanner = new Scanner(template);
            scanner.useDelimiter("\\A");
            String contenu = scanner.next();
            contenu = contenu.replace("#dataLabel#", this.complete(pathsubdivisionJS));
            // System.out.println(contenu);
            scanner.close();
            return contenu;
        } catch (Exception e) {
            throw e;
        }

    }

    // rends tout le contenu d'un fichier
    public String ReadTemplate(String path) throws Exception {
        File template = new File(path);
        Scanner scanner = new Scanner(template);
        scanner.useDelimiter("\\A");
        String contenu = scanner.next();
        scanner.close();
        return contenu;
    }

    // donne l'input correspondant au tye de donnee
    public String getDataType(Object obj) {
        // System.out.println(obj.getClass().getTypeName());
        if (obj.toString().contains("Date") == true || obj.toString().contains("Time") == true) {
            return "date";
        }
        if (obj.toString().compareToIgnoreCase("Number") == 0) {
            return "number";
        }
        return obj.toString();
    }

    public String getImport(String alls) {
        String[] all = alls.split("-");
        String rep = "";
        for (String type : all) {
            // System.out.println(type);

            rep = rep + "\n" + "import  " + type + ";" + "\n";
        }
        return rep;
    }

    // generate the ligne for one attribute of the ligne
    public String generateSubdivision(Object attribute, String attrName, String ligne) throws Exception {
        try {
            ligne = ligne.replaceAll("#dataLabel#", attrName);
            ligne = ligne.replaceAll("#dataType#", this.getDataType(attribute));
            return ligne;
        } catch (Exception e) {
            throw e;
        }

    }

    public String generateSubdivision2part(Object attribute, String attributeParam, String attrName, String ligne)
            throws Exception {
        try {
            ligne = ligne.replaceFirst("#dataLabel#", attrName);
            ligne = ligne.replace("#dataLabel#", attributeParam);
            ligne = ligne.replaceAll("#dataType#", this.getDataType(attribute));
            return ligne;
        } catch (Exception e) {
            throw e;
        }

    }

    // prends la l'url correspondant au paramatre
    public static HashMap<String, String> getFolder(String parameter) throws Exception {
        String file = Constantes.PARAMETRE_CONF;
        Properties props = new Properties();
        HashMap<String, String> properties = new HashMap<String, String>();
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            props.load(fileInputStream);
            properties.put(parameter, props.getProperty(parameter));
        } catch (Exception e) {
            throw new Exception("error during loading of" + file + ":" + e.getLocalizedMessage());
        } finally {
            return properties;
        }
    }

    // genere la partie script
    public String createPartJS(String pathTemplate, String pathsubdivision) throws Exception {
        try {
            File template = new File(pathTemplate);
            Scanner scanner = new Scanner(template);
            scanner.useDelimiter("\\A");
            String contenu = scanner.next();

            contenu = contenu.replace("#import#",
                    this.getImport(this.getFolder("import").get("import")));

            contenu = contenu.replace("#create#",
                    this.ReadTemplate(this.getFolder("functionCreatedTemplate").get("functionCreatedTemplate")));

            if (this.getName().compareToIgnoreCase("Create") == 0) {
                contenu = contenu.replace(
                        "  let obj = projectInfo;for (let key in obj) {let value = obj[key];if (typeof value === 'object' && value !== null) {obj[key] = obj[key].label;} }",
                        "");
                contenu = contenu.replace(
                        "const id = this.$route.params.id;",
                        "");
            }
            if (this.getName().compareToIgnoreCase("Edit") == 0) {
                contenu = contenu.replace(
                        "  let obj = projectInfo;for (let key in obj) {let value = obj[key];if (typeof value === 'object' && value !== null) {obj[key] = obj[key].label;} }",
                        "");
            }

            contenu = contenu.replace("#handleSave#",
                    this.ReadTemplate(this.getFolder("functionHandleSaveTemplate").get("functionHandleSaveTemplate")));
            contenu = contenu.replace("#handleDelete#",
                    this.ReadTemplate(
                            (this.getFolder("functionHandleDeleteTemplate").get("functionHandleDeleteTemplate"))));

            contenu = contenu.replace("#fetchProjectList#",
                    this.ReadTemplate(this.getFolder("fetchProjectList").get("fetchProjectList")));
            contenu = contenu.replace("#datas#",
                    this.complete(this.getFolder("dataVide").get("dataVide")));
            contenu = contenu.replace("#formdata#", this.complete(this.getFolder("formdata").get("formdata")));
            contenu = contenu.replace("#datasVide#",
                    this.complete(this.getFolder("data").get("data")));

            contenu = contenu.replace("#classLabel#", this.getData().getTableName());
            contenu = contenu.replace("#route#",
                    this.getRoute());
            contenu = contenu.replace("#routeCreate#",
                    this.getRouteCreate());
            contenu = contenu.replace("#routeDelete#",
                    this.getRouteDelete());
            contenu = contenu.replace("#dataLabel#", this.complete(pathsubdivision));
            // System.out.println(contenu);
            scanner.close();
            return contenu;
        } catch (Exception e) {
            throw e;
        }

    }

    // cree le fichier avec le nom et le contenu souhaite
    public void createFile(String fileName, String content) {
        try {
            // Create a new file object
            File file = new File(fileName);

            // Check if the file already exists
            if (file.createNewFile()) {
                System.out.println("File created: " + fileName);
            } else {
                System.out.println("File already exists: " + fileName);
            }

            // Create a FileWriter object to write to the file
            FileWriter writer = new FileWriter(fileName);

            // Write the string content to the file
            writer.write(content);

            // Close the FileWriter object
            writer.close();

            System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // creer le temolate integrale + generation su fichier
    public void createTemplate() throws Exception {
        File template = new File(this.getTemplatePath());
        try {
            Scanner scanner = new Scanner(template);
            scanner.useDelimiter("\\A");
            String contenu = scanner.next();
            contenu = contenu.replace("#dataLabel#", this.getData().getTableName().substring(0, 1).toUpperCase()
                    + this.getData().getTableName().substring(1).toString());
            contenu = contenu.replace("#data#", this.complete(this.getSubdivisionTemplate()));
            contenu = contenu.replace("#dataLegend#", this.complete(this.getDataLegendTemplate()));
            contenu = contenu.replace("#ProjectJS#", this.createPartJS(this.getJSPath(), this.getDataTemplate()));
            contenu = contenu.replace("#project#", this.getData().getClassName()
                    + this.getName());
            System.out.println("step 3");
            contenu = contenu.replace("#routeLink#",
                    this.completePageRoute(this.getFolder("routeLink").get("routeLink")));
            contenu = contenu.replace("#project#",
                    this.getData().getClassName().toLowerCase());
            scanner.close();
            this.createFile(
                    this.getFolder("path").get("path") + this.getData().getClassName()
                            + this.getName()
                            + ".vue",
                    contenu);

            if (this.getName().compareToIgnoreCase("edit") == 0 || this.getName().compareToIgnoreCase("Show") == 0) {
                Component.getRoutesWithParam().add(this.getData().getClassName() + this.getName());
            } else {
                Component.getRoutesWithoutParam().add(this.getData().getClassName() + this.getName());
            }

        } catch (Exception e) {
            throw e;
        }

    }
    public void login_create(String pathTemplate,String projectName)throws Exception{
        File template = new File(pathTemplate);
        Scanner scanner = new Scanner(template);
        scanner.useDelimiter("\\A");
        String contenu = scanner.next();

        contenu = contenu.replace("#path#", projectName);
        this.createFile(  this.getFolder("path").get("path")+"login_auth.vue",
            contenu);
    }
    // cree le fichier main.js
    public void getMainJsContent() throws Exception {

        try {
            File template = new File(Component.getFolder("main").get("main"));
            Scanner scanner = new Scanner(template);
            scanner.useDelimiter("\\A");
            String contenu = scanner.next();
            contenu = contenu.replace("#import#", this.completePage(this.getFolder("importMain").get("importMain")));
            contenu = contenu.replace("#path#", this.completePage(this.getFolder("pathMain").get("pathMain")));

            // System.out.println(contenu);
            scanner.close();
            this.createFile(
                    this.getFolder("mainPathFolder").get("mainPathFolder"),
                    contenu);
        } catch (Exception e) {
            throw e;
        }
    }

    // cree les liens dans le fichier main.js
    public String completePage(String path) throws Exception {
        String templateSubdivisions = "";
        try {
            String ligne = this.ReadTemplate(path);
            for (String paths : this.getRoutesWithParam()) {
                templateSubdivisions = templateSubdivisions + this.generateRouteWithParam(paths, ligne)
                        + "\n\t\t";

            }

            for (String paths : this.getRoutesWithoutParam()) {
                templateSubdivisions = templateSubdivisions + this.generateRoute(paths, ligne)
                        + "\n\t\t";
            }
        } catch (Exception e) {
            throw e;
        }

        return templateSubdivisions;

    }

    // genere les routes des boutons dans le tableau
    public String completePageRoute(String path) throws Exception {
        String templateSubdivisions = "";
        try {
            String ligne = this.ReadTemplate(path);
            for (String paths : this.getRoutesWithParam()) {
                if (paths.contains(this.getData().getClassName()) == true) {
                    templateSubdivisions = templateSubdivisions + this.generateRouteLink(paths, ligne)
                            + "\n\t\t";
                }

            }
        } catch (Exception e) {
            throw e;
        }

        return templateSubdivisions;

    }

    // cree le lien d'un bouton du tableau
    public String generateRouteLink(Object attribute, String ligne) throws Exception {
        try {
            ligne = ligne.replaceAll("#routeLink#", attribute.toString());
            if (attribute.toString().compareToIgnoreCase(this.getData().getClassName() + "Create") == 0) {
                ligne = ligne.replace("/${project.id}", "");
            }
            ligne = ligne.replaceAll("#routeLabel#",
                    attribute.toString().replace(this.getData().getClassName(), ""));
            return ligne;
        } catch (Exception e) {
            throw e;
        }

    }

    // creer les liens des bouttons avec parametre
    public String generateRouteWithParam(Object attribute, String ligne) throws Exception {
        try {
            ligne = ligne.replaceAll("#route#", attribute.toString());
            ligne = ligne.replaceAll("#routePath#", attribute.toString() + "/:id");
            return ligne;
        } catch (Exception e) {
            throw e;
        }

    }

    // creer les liens au niveau view
    public String generateRoute(Object attribute, String ligne) throws Exception {
        try {
            ligne = ligne.replaceAll("#routePath#", attribute.toString());
            ligne = ligne.replaceAll("#route#", attribute.toString());
            return ligne;
        } catch (Exception e) {
            throw e;
        }

    }

    // creer le lien vers les donnees
    public void setRoute() {
        if (this.getName().compareToIgnoreCase("List") == 0) {
            this.setRoute(this.getProjectName() + "/tocrud"
                    + this.getData().getClassName().toLowerCase() + ".do");
        }
        if (this.getName().compareToIgnoreCase("create") == 0) {
            this.setRoute(this.getProjectName() + "/insert"
                    + this.getData().getClassName().toLowerCase() + ".do");
        }
        if (this.getName().compareToIgnoreCase("delete") == 0) {
            this.setRoute(this.getProjectName() + "/delete"
                    + this.getData().getClassName().toLowerCase() + ".do");
        }
        if (this.getName().compareToIgnoreCase("edit") == 0) {
            this.setRoute(this.getProjectName() + "/update"
                    + this.getData().getClassName().toLowerCase() + ".do?id=${this.project.id}");
        }

    }

    Class<?>[] getClassesDansDossiers(String folder, String nomDePackage) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        Class<?>[] classing = null;
        File dossier = new File(folder + "/" + nomDePackage.replace(".", "/") + "/");

        try {
            File[] fichiersDansDossier = dossier.listFiles();
            System.out.println(fichiersDansDossier);
            if (fichiersDansDossier != null) { // Check if the array is not null
                System.out.println(fichiersDansDossier.length);

                for (File fichier : fichiersDansDossier) {
                    if (fichier.getName().endsWith(".class")) {
                        String nomDeClasse = nomDePackage + "."
                                + fichier.getName().substring(0, fichier.getName().length() - 6);
                        System.out.println(nomDeClasse);
                        classes.add(Class.forName(nomDeClasse));
                    }

                }

                classing = new Class[classes.size()];
                for (int i = 0; i < classes.size(); i++) {
                    classing[i] = classes.get(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur: Utils getClassesDansDossiers(String nomDePackage)");
        }

        return classing;
    }

    public Object[] translate_to_Object(String folder, String nomDePackage) throws Exception {
        Class<?>[] classes = getClassesDansDossiers(folder, nomDePackage);
        Object[] objects = new Object[classes.length];

        for (int i = 0; i < classes.length; i++) {
            try {
                objects[i] = classes[i].newInstance(); // Instantiate the class using reflection
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                // Handle the exception as needed
            }
        }

        return objects;
    }

    // set les menus dans le Sidebar
    public void setLinkInMenu(ArrayList<String> links, ArrayList<String> paths) throws Exception {
        String filepath = this.getFolder("sidebarpath").get("sidebarpath");
        String contenu = this.ReadTemplate(filepath);
        String template_link = "<li><router-link to=\"/#routerpath#\">#link#</router-link></li>";
        String all_links = "";
        String one_link = "";
        for (int i = 0; i < links.size(); i++) {
            one_link = template_link.replace("#link#", links.get(i));
            one_link = one_link.replace("#routerpath#", paths.get(i));
            one_link = one_link + "\n\t\t";
            all_links += one_link;
        }
        String home_link = "<router-link to=\"/#routerpath#\"><h2>#link#</h2></router-link>";
        home_link = home_link.replace("#routerpath#", "");
        home_link = home_link.replace("#link#", this.getProjectName());

        contenu = contenu.replace("#home_link#", home_link);
        contenu = contenu.replace("#router_link#", all_links);
        contenu = contenu.replace("#titre#", this.getProjectName());
        this.createFile(this.getFolder("path").get("path") + "SideBar.vue", contenu);
    }


    public static void deleteFilesInDirectory(Path directoryPath) throws IOException {
        // Vérifier si le chemin spécifié est un répertoire
        if (Files.isDirectory(directoryPath)) {
            // Parcourir les fichiers du répertoire
            try (Stream<Path> paths = Files.walk(directoryPath)) {
                paths.filter(Files::isRegularFile) // Filtrer les fichiers uniquement
                     .forEach(file -> {
                         try {
                             // Supprimer chaque fichier
                             Files.delete(file);
                             System.out.println("Le fichier " + file + " a été supprimé.");
                         } catch (IOException e) {
                             System.err.println("Erreur lors de la suppression du fichier " + file + " : " + e.getMessage());
                         }
                     });
            }
        } else {
            System.out.println("Le chemin spécifié n'est pas un répertoire.");
        }

    }
    public static void copyFolder(Path source, Path destination) throws IOException {
        // Vérifier si le chemin source est un dossier
        if (!Files.isDirectory(source)) {
            System.out.println("Le chemin source n'est pas un dossier.");
            return;
        }

        // Créer le dossier de destination s'il n'existe pas
        if (!Files.exists(destination)) {
            Files.createDirectories(destination);
        }

        // Parcourir les éléments du dossier source
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(source)) {
            for (Path entry : stream) {
                Path target = destination.resolve(entry.getFileName());
                // Si l'élément est un dossier, appeler récursivement copyFolder()
                if (Files.isDirectory(entry)) {
                    copyFolder(entry, target);
                } else {
                    // Copier le fichier
                    Files.copy(entry, target, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }
    public void GenerateDefaultFile(String filename) throws Exception {
        String filepath = this.getFolder(filename).get(filename);
        String contenu = this.ReadTemplate(filepath);
        contenu = contenu.replace("#Project#", this.getProjectName());
        this.createFile(this.getFolder("path").get("path") + filename + ".vue", contenu);
    }
}