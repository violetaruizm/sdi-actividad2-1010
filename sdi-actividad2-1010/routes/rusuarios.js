module.exports = function (app, swig, gestorBD) {


    app.get("/signup", function (req, res) {
        var respuesta = swig.renderFile('views/signUp.html', {});
        res.send(respuesta);
    });


    app.post('/signup', function (req, res) {

        if (req.body.email === null || req.body.email === "") {
            res.redirect("/signup?mensaje=El email no puede estar vacío");


        }


        if (req.body.password === null || req.body.password === "") {
            res.redirect("/signup?mensaje=La contraseña no puede estar vacía");
        }

        if (req.body.password.length >= 0 && req.body.password.length < 8) {
            res.redirect("/signup?mensaje=La contraseña debe tener más de 8 caracteres");

        }

        if (req.body.repassword.length >= 0 && req.body.repassword.length < 8) {
            res.redirect("/signup?mensaje=Repita la contraseña");

        }
        if (req.body.repassword != req.body.password) {
            res.redirect("/signup?mensaje=Las contraseñas no coinciden");

        }

        var seguro = app.get("crypto").createHmac('sha256', app.get('clave'))
            .update(req.body.password).digest('hex');

        var usuario = {
            email: req.body.email,
            password: seguro,
            name: req.body.name,
            surname: req.body.surname,
            money: 100,
            rol: "rol_estandar",
            valid: true
        }

        var criterio = {
            email: req.body.email
        }
        gestorBD.obtenerUsuarios(criterio, function (usuarios) {
            if (usuarios != null && usuarios.length != 0) {
                res.redirect("/signup?mensaje=El email ya está registrado. Inténtelo de nuevo con un " +
                    "email diferente");
            } else {
                gestorBD.insertarUsuario(usuario, function (id) {
                    if (id == null) {
                        //res.send("error al insertar")
                        res.redirect("/signup?mensaje=Error al registrar usuario");
                    } else {
                        //res.redirect("/identificarse");
                        res.redirect("/login?mensaje=Nuevo usuario registrado");
                    }
                })
            }
        })
    });


    app.get("/login", function (req, res) {
        var respuesta = swig.renderFile('views/logIn.html', {});
        res.send(respuesta);
    });


    app.post("/login", function (req, res) {
        var seguro = app.get("crypto").createHmac('sha256', app.get('clave'))
            .update(req.body.password).digest('hex');


        var criterio = {
            email: req.body.email,
            password: seguro
        }

        gestorBD.obtenerUsuarios(criterio, function (usuarios) {
            if (usuarios == null || usuarios.length == 0) {
                req.session.usuario = null;
                res.redirect("/login" +
                    "?mensaje=Email o password incorrecto" +
                    "&tipoMensaje=alert-danger ");
            } else {

                req.session.usuario = usuarios[0];
                if (usuarios[0].rol == "rol_estandar") {

                    res.redirect("/home");
                } else {

                    res.redirect("/homeAdmin");

                }
            }
        });
    });

    app.get("/home", function (req, res) {
        if (req.session.usuario === null) {

            res.redirect("/login");
        }
        if (req.session.usuario.rol != "rol_estandar") {
            res.redirect(("/homeAdmin?mensaje=No puede acceder a esa parte de la web"))

        } else {

            var respuesta = swig.renderFile('views/homeStandard.html', {user: req.session.usuario});
            res.send(respuesta);
        }
    });

    app.get("/homeAdmin", function (req, res) {
        if (req.session.usuario === null) {
            res.redirect("/login");
        }
        if (req.session.usuario.rol != "rol_admin") {
            res.redirect(("/home?mensaje=No puede acceder a esa parte de la web"))

        } else {
            var respuesta = swig.renderFile('views/homeAdmin.html', {
                user: req.session.usuario
            });
            res.send(respuesta);
        }
    });

    app.get("/logout", function (req, res) {
        var respuesta = swig.renderFile('views/logIn.html', {});
        req.session.usuario = null;
        res.send(respuesta);
    });

    app.get("/user/list", function (req, res) {
        if (req.session.usuario === null || req.session.usuario.rol != "rol_admin") {
            res.redirect("/home?mensaje=No puede acceder a esa parte de la web");
        } else {
            gestorBD.obtenerUsuarios({}, function (usuarios) {
                let respuesta = swig.renderFile('views/userList.html', {usersList: usuarios});
                res.send(respuesta);
            })
        }
        ;
    });

    app.post("/user/delete", function (req, res) {

let criterio = {
    valid : false
};


    });
};