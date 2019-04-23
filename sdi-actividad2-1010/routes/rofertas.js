module.exports = function (app, swig, gestorBD) {

    app.get("/sale/new", function (req, res) {
        if (req.session.usuario == null) {
            res.redirect("/login");
        }
        if (req.session.usuario.rol == "rol_admin") {
            res.redirect("/homeAdmin?mensaje=No puede acceder a esa parte de la web");
        }
        var respuesta = swig.renderFile('views/newSale.html', {});
        res.send(respuesta);
    });


    app.post("/sale/new", function (req, res) {

        if (req.session.usuario == null) {
            res.redirect("/login");
        }
        console.log(req.body.title);
        if (req.body.title === null || req.body.title === "") {
            res.redirect("/sale/new?mensaje=El título no puede estar vacio");
        }
        if (req.body.description === null || req.body.description === "") {
            res.redirect("/sale/new?mensaje=La descripción no puede estar vacio");
        }
        if (req.body.price === null || req.body.price === "") {
            res.redirect("/sale/new?mensaje=El precio no puede estar vacio");

        }

        if (typeof (parseInt(req.body.price)) != "number") {

            res.redirect("/sale/new?mensaje=Introduzca un precio correcto");
        }

        var price = parseInt(req.body.price);

        if (price <= 0) {
            res.redirect("/sale/new?mensaje=El precio debe ser mayor que 0€");
        }
        var datePost = new Date();

        var oferta = {
            title: req.body.title,
            description: req.body.description,
            price: price,
            valid: true,
            owner: req.session.usuario,
            buyer: null,
            date: datePost,
            onsale : true
        }

        gestorBD.insertarOferta(oferta, function (id) {
            if (id == null) {
                //res.send("error al insertar")
                res.redirect("/sale/new?mensaje=Error al publicar la oferta");
            } else {
                //res.redirect("/identificarse");
                res.redirect("/sale/new?mensaje=La oferta se publicó correctamente");
            }
        });
    });

    app.get("/sale/own", function (req, res) {
        if (req.session.usuario === null || req.session.usuario.rol === "rol_admin") {
            res.redirect("/home?mensaje=No puede acceder a esa parte de la web");
        } else {
            var criterio = {owner: req.session.usuario};
            gestorBD.obtenerOfertas
            (criterio, function (ofertas) {
                console.log(ofertas)
                var respuesta = swig.renderFile('views/postedSales.html', {salesList: ofertas});
                res.send(respuesta);
            })
        }

    });

    app.get("/sale/delete/:id", function (req, res) {

        var criterioNuevo = {valid : false};
        var criterio = { _id : gestorBD.mongo.ObjectID(req.params.id) };

        gestorBD.deleteSale(criterio, criterioNuevo, function (ofertas) {


            if (ofertas == null || ofertas.length == 0) {
                res.redirect("/sale/own" +
                    "?mensaje=La oferta no pudo eliminarse correctamente");
            } else {

                res.redirect("/sale/own" +
                    "?mensaje=La oferta se eliminó correctamente");
            }
        });

    });

    app.get("/sale/bought", function (req, res) {
        if (req.session.usuario === null || req.session.usuario.rol === "rol_admin") {
            res.redirect("/home?mensaje=No puede acceder a esa parte de la web");
        } else {
            var criterio = {buyer: req.session.usuario};
            gestorBD.obtenerOfertas
            (criterio, function (ofertas) {
                console.log(ofertas)
                var respuesta = swig.renderFile('views/boughtSales.html', {salesList: ofertas});
                res.send(respuesta);
            })
        }

    });

};


