export function graphDateFormatter(utcString) {
    const localDate = new Date(utcString);
    const currentDate = new Date();
    if (!isNaN(localDate.getTime())) {
        if (localDate.getFullYear() < currentDate.getFullYear()) {
            return localDate.toLocaleDateString('en-US', {month: "short"}) + ' ' +
            ('00' + localDate.getDate()).slice(-2) + ', ' +
            localDate.getFullYear();
        } else {
            return localDate.toLocaleDateString('en-US', {month: "short"}) + ' ' +
            ('00' + localDate.getDate()).slice(-2);
        }
    }
    return null;
}
