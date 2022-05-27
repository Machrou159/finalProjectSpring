$(document)
    .ready(function() {
      $('.ui.form')
        .form({
          fields: {
            password: {
              identifier  : 'password',
              rules: [
                {
                  type   : 'length[6]',
                  prompt : 'Votre mot de passe doit contenir 6 caractères minimum'
                }
              ]
            }
          }
        })
      ;
    })
  ;

function redirect(url){
  window.location.href = url;
}