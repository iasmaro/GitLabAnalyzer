export function createStartDate(){
    return {Year:'', Month:'', Day:'', Hours:'', Minutes:'', Seconds:''}
}

export function createEndDate() {
    let currentDate = new Date();
    return {Year:currentDate.getFullYear().toString(), Month:currentDate.getMonth().toString(), Day:currentDate.getDate().toString(), 
        Hours:currentDate.getHours().toString(), Minutes:currentDate.getMinutes().toString(), Seconds:currentDate.getSeconds().toString()
    };
}