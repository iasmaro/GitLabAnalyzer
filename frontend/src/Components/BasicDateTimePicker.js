import React, { useState } from 'react';
import DateFnsUtils from '@date-io/date-fns';
import { DateTimePicker, MuiPickersUtilsProvider } from '@material-ui/pickers';
import { Form } from 'react-bootstrap';

const BasicDateTimePicker = (props) => {

    const {date, setDate} = props;

    const [selectedDate, handleDateChange] = useState(new Date());

    return (
        <Form>
            <MuiPickersUtilsProvider utils={DateFnsUtils}>
                <DateTimePicker 
                    variant="inline"
                    value={selectedDate} 
                    onChange={handleDateChange} />
            </MuiPickersUtilsProvider>
        </Form>
    );
}

export default BasicDateTimePicker;