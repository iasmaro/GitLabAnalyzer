export function utcToLocal(utcString) {
    var localDate = new Date(utcString);
    var currentDate = new Date();
    if (!isNaN(localDate.getTime())) {
        if (localDate.getFullYear() < currentDate.getFullYear()) {
            return localDate.toLocaleDateString('en-US', {month: "short"}) + ' ' +
            ('00' + localDate.getDate()).slice(-2) + ', ' +
            localDate.getFullYear() + ' at ' +
            ('00' + localDate.getHours()).slice(-2) + ':' +
            ('00' + localDate.getMinutes()).slice(-2);
        } else {
            return localDate.toLocaleDateString('en-US', {month: "short"}) + ' ' +
            ('00' + localDate.getDate()).slice(-2) + ' at ' +
            ('00' + localDate.getHours()).slice(-2) + ':' +
            ('00' + localDate.getMinutes()).slice(-2);
        }
    }
    return null;
}
