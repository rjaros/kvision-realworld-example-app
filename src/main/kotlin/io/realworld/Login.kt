package io.realworld

import pl.treksoft.kvision.core.Container
import pl.treksoft.kvision.form.form
import pl.treksoft.kvision.form.text.TextInput
import pl.treksoft.kvision.form.text.TextInputType
import pl.treksoft.kvision.form.text.textInput
import pl.treksoft.kvision.html.button
import pl.treksoft.kvision.html.div
import pl.treksoft.kvision.html.fieldset
import pl.treksoft.kvision.html.h1
import pl.treksoft.kvision.html.link
import pl.treksoft.kvision.html.p
import pl.treksoft.kvision.html.ul

fun Container.loginPage(state: ConduitState) {
    div(className = "auth-page") {
        div(className = "container page") {
            div(className = "row") {
                div(className = "col-md-6 offset-md-3 col-xs-12") {
                    h1("Sign in", className = "text-xs-center")
                    p(className = "text-xs-center") {
                        link("Need an account?", "#${View.REGISTER.url}")
                    }
                    if (state.loginError) {
                        ul(listOf("email or password is invalid"), className = "error-messages")
                    }
                    form {
                        lateinit var emailInput: TextInput
                        lateinit var passwordInput: TextInput
                        fieldset(className = "form-group") {
                            emailInput = textInput(className = "form-control form-control-lg") {
                                placeholder = "Email"
                            }
                        }
                        fieldset(className = "form-group") {
                            passwordInput =
                                textInput(TextInputType.PASSWORD, className = "form-control form-control-lg") {
                                    placeholder = "Password"
                                }
                        }
                        button("Sign in", className = "btn btn-lg btn-primary pull-xs-right").onClick {
                            ConduitManager.login(emailInput.value, passwordInput.value)
                        }
                    }
                }
            }
        }
    }
}
