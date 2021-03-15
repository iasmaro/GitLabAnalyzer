import React, { useState } from 'react';
import DateFnsUtils from '@date-io/date-fns';
import { DateTimePicker, MuiPickersUtilsProvider } from '@material-ui/pickers';
import { Form } from 'react-bootstrap';

const FormattedDateTimePicker = (props) => {

    const {startName, endName, setStartDate, setEndDate} = props || {};

    const [selectedStartDate, setSelectedStartDate] = useState(new Date());
    const [selectedEndDate, setSelectedEndDate] = useState(new Date());

    const handleStartDateChange = (date) => {
        setStartDate(date);
        setSelectedStartDate(date);
    };

    const handleEndDateChange = (date) => {
        setEndDate(date);
        setSelectedEndDate(date);
    };

    return (
        <Form>
            <MuiPickersUtilsProvider utils={DateFnsUtils}>
                <DateTimePicker 
                    variant="inline"
                    label={startName}
                    format="yyyy/MM/dd HH:mm"
                    value={selectedStartDate} 
                    maxDate={selectedEndDate}
                    onChange={handleStartDateChange} />
                <DateTimePicker 
                    variant="inline"
                    label={endName}
                    format="yyyy/MM/dd HH:mm"
                    minDate={selectedStartDate}
                    value={selectedEndDate} 
                    onChange={handleEndDateChange} />
            </MuiPickersUtilsProvider>
        </Form>
    );
}

export default FormattedDateTimePicker;