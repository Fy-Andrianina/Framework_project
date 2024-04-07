<%@page import="my_boo.entities.*" %>
<%
    Livre[] liste=(Livre[])request.getAttribute("o");
    Auteur[] auteur=(Auteur[])request.getAttribute("auteurs");Editeur[] editeur=(Editeur[])request.getAttribute("editeurs");
%>
<div class="container-fluid pt-4 px-4">
    <div class="row g-4">
        <div class="col-sm-12 col-xl-6 offset-3">
            <div class="bg-secondary rounded h-100 p-4">
                <h6 class="mb-4">Liste de livre</h6>
                <table class="table">
                    <thead>
                        <tr>                            
                            <th scope="col">Id</th><th scope="col">Label</th><th scope="col">Auteur</th><th scope="col">Editeur</th><th scope="col">Date publication</th>
                        </tr>
                    <tbody>
                        <% for(Livre o:liste){ %>
                            <tr>
                                <td><%= o.getId() %></td><td><%= o.getLabel() %></td><td><%= o.getAuteur().getLabel() %></td><td><%= o.getEditeur().getLabel() %></td><td><%= o.getDatePublication() %></td>
                                <td>
                                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modifiermodal<%= o.getId() %>">
                                        <p><i class="bi bi-pencil"></i>Modifier</p>
                                    </button>
                                    <div class="modal fade" id="modifiermodal<%= o.getId() %>" tabindex="-1" aria-labelledby="modifiermodalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered">
                                        <div class="modal-content">
                                        <form action="/My_boo/updatelivre.do" method="put">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="modifiermodalLabel">Modifier un livre</h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <input type="hidden" name="id" value="<%= o.getId() %>">
                                            <div class="mb-3"> 
    <label for="labelinput" class="form-label">Label
    </label> 
    <input type="text" class="form-control" id="labelinput" aria-describedby="labelhelp" name="label" value="<%= o.getLabel() %>"> 
</div>
<div class="form-floating mb-3"> 
    <select class="form-select" id="auteurSelect" name="auteur">
        <% for(Auteur f:auteur){ %>
        <option value="<%= f.getId() %>" <% if(f.getId().equals(o.getAuteur().getId())){ out.print("selected"); } %>>
            <%= f.getLabel() %>
        </option>
        <% } %>
    </select> 
    <label for="auteurSelect">
        Auteur
    </label> 
</div>
<div class="form-floating mb-3"> 
    <select class="form-select" id="editeurSelect" name="editeur">
        <% for(Editeur f:editeur){ %>
        <option value="<%= f.getId() %>" <% if(f.getId().equals(o.getEditeur().getId())){ out.print("selected"); } %>>
            <%= f.getLabel() %>
        </option>
        <% } %>
    </select> 
    <label for="editeurSelect">
        Editeur
    </label> 
</div>
<div class="mb-3"> 
    <label for="datePublicationinput" class="form-label">Date publication
    </label> 
    <input type="date" class="form-control" id="datePublicationinput" aria-describedby="datePublicationhelp" name="datePublication" value="<%= o.getDatePublication() %>"> 
</div>

                                        </div>
                                        <div class="modal-footer">
                                            <button type="submit" class="btn btn-primary">Valider</button>
                                        </div>
                                        </form>
                                        </div>
                                    </div>
                                    </div>
                                </td>
                                <td><form action="/My_boo/deletelivre.do" method="delete">
                                    <input type="hidden" name="id" value="<%= o.getId() %>">
                                    <button type="submit" class="btn btn-danger">
                                        <p><i class="bi bi-trash"></i>Supprimer</p>
                                    </button>
                                </form></td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#ajoutmodal">
                    <p><i class="bi bi-plus"></i>Ajouter</p>
                </button>
            </div>
        </div>
        <!-- Button modal ajout -->

        <!-- Modal Ajout -->
        <div class="modal fade" id="ajoutmodal" tabindex="-1" aria-labelledby="ajoutmodalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
            <form action="/My_boo/insertlivre.do" method="post">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="ajoutmodalLabel">Ajouter un livre</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3"> 
    <label for="labelinput" class="form-label">Label
    </label> 
    <input type="text" class="form-control" id="labelinput" aria-describedby="labelhelp" name="label"> 
</div>
<div class="form-floating mb-3"> 
    <select class="form-select" id="auteurSelect" name="auteur">
        <% for(Auteur f:auteur){ %>
        <option value="<%= f.getId() %>">
            <%= f.getLabel() %>
        </option>
        <% } %>
    </select> 
    <label for="auteurSelect">
        Auteur
    </label> 
</div>
<div class="form-floating mb-3"> 
    <select class="form-select" id="editeurSelect" name="editeur">
        <% for(Editeur f:editeur){ %>
        <option value="<%= f.getId() %>">
            <%= f.getLabel() %>
        </option>
        <% } %>
    </select> 
    <label for="editeurSelect">
        Editeur
    </label> 
</div>
<div class="mb-3"> 
    <label for="datePublicationinput" class="form-label">Date publication
    </label> 
    <input type="date" class="form-control" id="datePublicationinput" aria-describedby="datePublicationhelp" name="datePublication"> 
</div>

            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-primary">Valider</button>
            </div>
            </form>
            </div>
        </div>
        </div>
    </div>
</div>
