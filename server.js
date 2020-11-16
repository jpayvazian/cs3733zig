/*
THIS CODE IS ONLY SO I CAN EASILY TEST THE HTML/JS.
THIS CODE WILL NOT BE IN THE FINAL REPOSITORY

If you wish to test this yourself, install node.js and then run
"node server.js" while in your cloned repository.
*/
const http = require('http'),
      fs   = require('fs'),
      port = 3000

const server = http.createServer( function( request,response ) {
  switch( request.url ) {
    case '/':
      sendFile( response, 'html/index.html' )
      break
    case '/index.html':
      sendFile( response, 'html/index.html' )
      break
    case '/admin.html':
      sendFile(response, 'html/admin.html')
      break
    default:
      sendFile( response, 'js/script.js' )
  }
})

server.listen( process.env.PORT || port )

const sendFile = function( response, filename ) {
   fs.readFile( filename, function( err, content ) {
     file = content
     response.end( content, 'utf-8' )
   })
}
//delete me