export function utcToLocal(utcString) {
    var localDate = new Date(utcString);
    if (!isNaN(localDate.getTime())) {
        return ('00' + (localDate.getMonth() + 1)).slice(-2) + '/' +
            ('00' + localDate.getDate()).slice(-2) + '/' +
            localDate.getFullYear() + ' at ' +
            ('00' + localDate.getHours()).slice(-2) + ':' +
            ('00' + localDate.getMinutes()).slice(-2);
    }
    return null;
}
