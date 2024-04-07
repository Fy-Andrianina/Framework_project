<%@page import="my_second_Boo.entities.*" %>
<%
    Utilisateur[] liste=(Utilisateur[])request.getAttribute("o");
    
%>
<div class="container-fluid pt-4 px-4">
    <div class="row g-4">
        <div class="col-sm-12 col-xl-6 offset-3">
            <div class="bg-secondary rounded h-100 p-4">
                <h6 class="mb-4">Liste de utilisateur</h6>
                <table class="table">
                    <thead>
                        <tr>                            
                            <th scope="col">Id</th><th scope="col">Login</th><th scope="col">Pwd</th>
                        </tr>
                    <tbody>
                        <% for(Utilisateur o:liste){ %>
                            <tr>
                                <td><%= o.getId() %></td><td><%= o.getLogin() %></td><td><%= o.getPwd() %></td>
                                <td>
                                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modifiermodal<%= o.getId() %>">
                                        <p><i class="bi bi-pencil"></i>Modifier</p>
                                    </button>
                                    <div class="modal fade" id="modifiermodal<%= o.getId() %>" tabindex="-1" aria-labelledby="modifiermodalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered">
                                        <div class="modal-content">
                                        <form action="/My_second_Boo/updateutilisateur.do" method="put">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="modifiermodalLabel">Modifier un utilisateur</h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <input type="hidden" name="id" value="<%= o.getId() %>">
                                            <div class="mb-3"> 
    <label for="logininput" class="form-label">Login
    </label> 
    <input type="text" class="form-control" id="logininput" aria-describedby="loginhelp" name="login" value="<%= o.getLogin() %>"> 
</div>
<div class="mb-3"> 
    <label for="pwdinput" class="form-label">Pwd
    </label> 
    <input type="text" class="form-control" id="pwdinput" aria-describedby="pwdhelp" name="pwd" value="<%= o.getPwd() %>"> 
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
                                <td><form action="/My_second_Boo/deleteutilisateur.do" method="delete">
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
            <form action="/My_second_Boo/insertutilisateur.do" method="post">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="ajoutmodalLabel">Ajouter un utilisateur</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3"> 
    <label for="logininput" class="form-label">Login
    </label> 
    <input type="text" class="form-control" id="logininput" aria-describedby="loginhelp" name="login"> 
</div>
<div class="mb-3"> 
    <label for="pwdinput" class="form-label">Pwd
    </label> 
    <input type="text" class="form-control" id="pwdinput" aria-describedby="pwdhelp" name="pwd"> 
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
