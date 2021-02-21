export function createStartDate(createdDate){
    var localDate = utcToLocal(createdDate);
    return {Year:localDate.getFullYear().toString(), Month:(localDate.getMonth()+1).toString(), Day:localDate.getDate().toString(), 
        Hours:localDate.getHours().toString(), Minutes:localDate.getMinutes().toString(), Seconds:localDate.getSeconds().toString()
    };
}

export function createEndDate() {
    let currentDate = new Date();
    return {Year:currentDate.getFullYear().toString(), Month:(currentDate.getMonth()+1).toString(), Day:currentDate.getDate().toString(), 
        Hours:currentDate.getHours().toString(), Minutes:currentDate.getMinutes().toString(), Seconds:currentDate.getSeconds().toString()
    };
}

export function utcToLocal(utcString){
    var localDate = new Date(utcString);
    return localDate;
}

export function localToUtc(date){
    var localDate = new Date(parseInt(date.Year), parseInt(date.Month-1), parseInt(date.Day), 
        parseInt(date.Hours), parseInt(date.Minutes), parseInt(date.Seconds));
    return localDate.toISOString();
}
