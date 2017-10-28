application {
    title = 'hack-nott'
    startupGroups = ['hackNott']
    autoShutdown = true
}
mvcGroups {
    // MVC Group for "hackNott"
    'hackNott' {
        model      = 'org.example.HackNottModel'
        view       = 'org.example.HackNottView'
        controller = 'org.example.HackNottController'
    }
}