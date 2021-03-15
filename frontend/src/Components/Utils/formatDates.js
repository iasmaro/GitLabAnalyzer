export function utcToLocal(utcString) {
    var localDate = new Date(utcString);
    if (!isNaN(localDate.getTime())) {
        // return (localDate.getMonth() + 1) + '/' + localDate.getDate() + '/' + localDate.getFullYear()
        //     + ' at ' +  localDate.getHours() + ':' + localDate.getMinutes();
        return (localDate.toDateString() + ' ' + localDate.toTimeString());
    }
}

export function localToUtc(date) {
    var localDate = new Date(parseInt(date.Year), parseInt(date.Month-1), parseInt(date.Day), 
        parseInt(date.Hours), parseInt(date.Minutes), parseInt(date.Seconds));
    if (!isNaN(localDate.getTime())) {
        return localDate.toISOString();
    }
}