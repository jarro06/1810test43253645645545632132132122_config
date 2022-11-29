 
listView('1810test43253645645545632132132122 Jobs') {
    description('1810test43253645645545632132132122 Jobs')
    jobs {
        regex('1810test43253645645545632132132122_.+')
    }
    columns {
        status()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
        buildButton()
    }
}
