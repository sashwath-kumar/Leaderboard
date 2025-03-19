window.onload = function() {
  window.ui = SwaggerUIBundle({
    url: "./openapi.json",
    dom_id: '#swagger-ui',
    deepLinking: true,
    presets: [
      SwaggerUIBundle.presets.apis,
      SwaggerUIStandalonePreset
    ],
    plugins: [
      SwaggerUIBundle.plugins.DownloadUrl
    ],
    layout: "StandaloneLayout",
    requestInterceptor: function(request) {
      const apiUrl = 'https://sashwath-kumar.github.io/'; 
      if (request.url.startsWith('/')) {
        request.url = apiUrl + request.url;
      }
      return request;
    }
  });
};
