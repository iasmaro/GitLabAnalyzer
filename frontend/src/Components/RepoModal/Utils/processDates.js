export function utcToLocal(utcString){
    var localDate = new Date(utcString);
    return localDate;
}

export function localToUtc(year, month, date, hour, minute, second){
    var localDate = new Date(year, month-1, date, hour, minute, second);
    return localDate.toISOString();
}