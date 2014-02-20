<%@ include file="/WEB-INF/stylesheets/include.jsp" %>
<div class="portlet-html">
    <div class="portlet-body">
        <div id="page" class="container-fluid">
            <!-- wrapper for mmenu -->
            <div id="favs" ng-controller="AppList2Ctrl">
                <div class="row">
                    <div class="col-sm-12">
                        <h2 class="h2">
                            Mes favoris
                            <span class="pull-right">
                             <a href="#catalog" class="btn btn-link"><span class="fa fa-bars fa-2x" /><span class="sr-only">Ouvrir le catalogue</span></a>
					</span>
                        </h2>
                    </div>
                </div>
                <div class="row" ng-repeat="application in applications">
                    <div class="btn-group">
                        <a class="btn btn-link dropdown-toggle" data-toggle="dropdown" href="{{application.url}}">
                            <span>{{application.title}}</span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#"><i class="fa fa-external-link"></i>Ouvrir l'application</a></li>
                            <li><a href="#"><i class="fa fa-eraser"></i> Supprimer des favoris</a></li>
                        </ul>
                    </div>
                </div>
            </div>


            <script type="text/ng-template" id="menu_renderer.html">
                <a href="#" class="domain">{{domaine.wording}}</a>

                <ng-switch on="domaine.applications.length > 0">
                    <span ng-switch-when="true">
                        <ul>
                            <li class="mm-label" ng-repeat="application in domaine.applications">
                                <a href="{{application.url}}">{{application.title}}</a>
                            </li>
                        </ul>
                    </span>
                </ng-switch>

                <ng-switch on="domaine.domaines.length > 0">
                    <span ng-switch-when="true">
                        <ul>
                            <li ng-repeat="domaine in domaine.domaines" ng-include="'menu_renderer.html'"></li>
                        </ul>
                    </span>
                </ng-switch>
            </script>

            <div id="catalog" ng-controller="DomainListCtrl">
                <div>
                    <ul>
                        <li ng-repeat="domaine in domaines" ng-include="'menu_renderer.html'"></li>
                    </ul>
                </div>
            </div>
        </div>

    </div>


</div>

<!-- </div> -->
<script type="text/javascript">

    $(function () {
        $("#catalog").mmenu({
            //                    classes: "mm-fullscreen",
            classes: "mm-light",
            position: "right",
            counters: true,
            searchfield: {
                add: true,
                search: true,
                placeholder: 'Rechercher',
                noResults: 'Aucun résultat'
            },
            moveBackground: true,
            labels: {
                fixed: true
            },
            header: {
                add: true,
                update: true,
                title: 'Catalogue des applications'
            }
        },
        {
            pageSelector:'#page'
        }
        );

    });


</script>