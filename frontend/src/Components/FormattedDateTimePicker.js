import React, { useState } from 'react';
import DateFnsUtils from '@date-io/date-fns';
import { DateTimePicker, MuiPickersUtilsProvider } from '@material-ui/pickers';
import { Form } from 'react-bootstrap';

const FormattedDateTimePicker = (props) => {

    const {name, setDate} = props;

    const [selectedDate, setSelectedDate] = useState(new Date());

    const handleDateChange = (date) => {
        setDate(date);
        setSelectedDate(date);
    };

    return (
        <Form>
            <MuiPickersUtilsProvider utils={DateFnsUtils}>
                <DateTimePicker 
                    variant="inline"
                    label={name}
                    format="yyyy/MM/dd HH:mm"
                    value={selectedDate} 
                    onChange={handleDateChange} />
            </MuiPickersUtilsProvider>
        </Form>
    );
}

export default FormattedDateTimePicker;