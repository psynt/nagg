application {
    title = 'hack-nott'
    startupGroups = ['CalendarView']
    autoShutdown = true
}
mvcGroups {
    // MVC Group for "hackNott"
    'hackNott' {
        model      = 'org.example.HackNottModel'
        view       = 'org.example.HackNottView'
        controller = 'org.example.HackNottController'
    }
    'CalendarView' {
        model      = 'calendar.CalendarModel'
        view       = 'calendar.CalendarView'
        controller = 'calendar.CalendarController'
    }
}