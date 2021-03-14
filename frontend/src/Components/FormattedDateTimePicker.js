import React, { useState } from 'react';
import DateFnsUtils from '@date-io/date-fns';
import { DateTimePicker, MuiPickersUtilsProvider } from '@material-ui/pickers';
import { Form } from 'react-bootstrap';

import { date } from 'Constants/constants';

const FormattedDateTimePicker = (props) => {

    const {startName, endName, setStartDate, setEndDate, readOnly} = props || {};

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
                    format={date.FULL_DATE_TIME}
                    value={selectedStartDate} 
                    maxDate={selectedEndDate}
                    readOnly={readOnly}
                    onChange={handleStartDateChange} />
                <DateTimePicker 
                    variant="inline"
                    label={endName}
                    format={date.FULL_DATE_TIME}
                    minDate={selectedStartDate}
                    value={selectedEndDate} 
                    readOnly={readOnly}
                    onChange={handleEndDateChange} />
            </MuiPickersUtilsProvider>
        </Form>
    );
}

export default FormattedDateTimePicker;