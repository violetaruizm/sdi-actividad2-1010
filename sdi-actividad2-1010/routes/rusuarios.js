module.exports = function (app, swig, gestorBD) {

    app.get("/signup", function (req, res) {
        let respuesta = swig.renderFile('views/signUp.html', {});
        app.get('logger').info('Sign up:se va a mostrar la página de registro');
        res.send(respuesta);
    });

    app.post('/signup', function (req, res) {

        if (req.body.email === null || req.body.email === "") {
            app.get('logger').error('Sign up: el email no puede estar vacío');
            res.redirect("/signup?mensaje=El email no puede estar vacío");
        }
        if (req.body.password === null || req.body.password === "") {
            app.get('logger').error('Sign up:la contraseña no puede estar vacía');
            res.redirect("/signup?mensaje=La contraseña no puede estar vacía");
        }
        if (req.body.password.length >= 0 && req.body.password.length < 8) {
            app.get('logger').error('Sign up: la contraseña debe tener más de 8 caracteres');
            res.redirect("/signup?mensaje=La contraseña debe tener más de 8 caracteres");
        }
        if (req.body.repassword.length >= 0 && req.body.repassword.length < 8) {
            app.get('logger').error('Sign up: repita la contraseña');
            res.redirect("/signup?mensaje=Repita la contraseña");
        }
        if (req.body.repassword !== req.body.password) {
            app.get('logger').error('Sign up: las contraseñas no coinciden');
            res.redirect("/signup?mensaje=Las contraseñas no coinciden");
        }

        let seguro = app.get("crypto").createHmac('sha256', app.get('clave'))
            .update(req.body.password).digest('hex');

        let usuario = {
            email: req.body.email,
            password: seguro,
            name: req.body.name,
            surname: req.body.surname,
            money: 100,
            rol: "rol_estandar",
            valid: true
        };

        let criterio = {
            email: req.body.email
        };
        gestorBD.obtenerUsuarios(criterio, function (usuarios) {
            if (usuarios != null && usuarios.length !== 0) {
                app.get('logger').error('Sign up: el email ya está registrado');
                res.redirect("/signup?mensaje=El email ya está registrado. Inténtelo de nuevo con un " +
                    "email diferente");
            } else {
                gestorBD.insertarUsuario(usuario, function (id) {
                    if (id == null) {
                        //res.send("error al insertar")
                        app.get('logger').error('Sign up: error al registrar el usuario');
                        res.redirect("/signup?mensaje=Error al registrar usuario");
                    } else {
                        //res.redirect("/identificarse");
                        app.get('logger').info('Sign up: usuario registrado correctamente. Redirigiendo a login');
                        res.redirect("/login?mensaje=Nuevo usuario registrado");
                    }
                })
            }
        })
    });


    app.get("/login", function (req, res) {
        let respuesta = swig.renderFile('views/logIn.html', {});
        app.get('logger').info('Log in: se va a mostrar la página de login');
        res.send(respuesta);
    });


    app.post("/login", function (req, res) {
        let seguro = app.get("crypto").createHmac('sha256', app.get('clave'))
            .update(req.body.password).digest('hex');

        let criterio = {
            email: req.body.email,
            password: seguro
        };

        gestorBD.obtenerUsuarios(criterio, function (usuarios) {
            if (usuarios == null || usuarios.length === 0) {
                req.session.usuario = null;
                app.get('logger').error('Log in: email o password incorrectos');
                res.redirect("/login" +
                    "?mensaje=Email o password incorrecto" +
                    "&tipoMensaje=alert-danger ");
            } else {
                if (usuarios[0].valid) {
                    let usuario = usuarios[0];
                    delete usuario.password;
                    req.session.usuario = usuario;
                    if (usuarios[0].rol === "rol_estandar") {
                        app.get('logger').info('Log in: log in usuario estándar correcto. Redirigiendo a página personal');
                        res.redirect("/home");
                    } else {
                        app.get('logger').info('Log in: log in usuario admin correcto. Redirigiendo a página personal');
                        res.redirect("/homeAdmin");
                    }
                } else {
                    app.get('logger').error('Log in: email o password no son correctos');
                    res.redirect("/login?mensaje=El email o password no son correctos");
                }
            }
        });
    });

    app.get("/home", function (req, res) {
        app.get('logger').info('Home: se va a mostrar la página principal de un usuario estándar');

        let respuesta = swig.renderFile('views/homeStandard.html', {user: req.session.usuario});
        res.send(respuesta);

    });

    app.get("/homeAdmin", function (req, res) {
        let respuesta = swig.renderFile('views/homeAdmin.html', {
            user: req.session.usuario
        });
        app.get('logger').info('Home Admin: se va a mostrar la página principal de un usuario admin');
        res.send(respuesta);
    });

    app.get("/logout", function (req, res) {
        let respuesta = swig.renderFile('views/logIn.html', {});

        req.session.usuario = undefined;
        app.get('logger').info('LogOut: se ha finalizado la sesión del usuario. Redirigiendo a página de log in');
        res.send(respuesta);
    });

    app.get("/user/list", function (req, res) {
        gestorBD.obtenerUsuarios({}, function (usuarios) {
            let respuesta = swig.renderFile('views/userList.html', {usersList: usuarios});
            app.get('logger').info('Listado de usuarios: se va a mostrar el listado de usuarios');
            res.send(respuesta);
        });
    });

    app.post("/user/delete", function (req, res) {
        let criterio;
        let criterioOfertas;

        if (typeof (req.body.email) == "object") {
            criterio = {email: {$in: req.body.email}};
            criterioOfertas = {owner: {$in: req.body.email}};
        }
        if (typeof (req.body.email) == "string") {
            criterio = {email: req.body.email};
            criterioOfertas = {owner: req.body.email};
        }
        let nuevoCriterio = {valid: false};
        console.log(req.body.email);

        if (criterio != null) {
            gestorBD.deleteUsers(criterio, nuevoCriterio, function (usuarios) {

                if (usuarios == null || usuarios.length === 0) {
                    app.get('logger').error('Borrado usuario: Los usuarios no pudieron eliminarse');
                    res.redirect("/user/list" +
                        "?mensaje=Los usuarios no pudieron eliminarse");
                } else {
                    gestorBD.deleteOfertas(criterioOfertas, nuevoCriterio, function (ofertas) {
                        if (ofertas === null) {
                            app.get('logger').error('Borrado usuario: Las ofertas del usuario no pudieron borrarse');
                            res.redirect("/user/list" +
                                "?mensaje= Las ofertas del usuario no pudieron borrarse");
                        } else {
                            app.get('logger').info('Borrado usuarios: Los usuarios se eliminaron correctamente');
                            res.redirect("/user/list" +
                                "?mensaje=Los usuarios se eliminaron correctamente");
                        }
                    })
                }
            });
        } else {
            res.redirect("/user/list");
        }


    });
};
