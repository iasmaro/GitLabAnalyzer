import React, { useState } from 'react';
import DateFnsUtils from '@date-io/date-fns';
import { DateTimePicker, MuiPickersUtilsProvider } from '@material-ui/pickers';
import { Form, Row, Col } from 'react-bootstrap';

import { modal } from 'Constants/constants';

const RepoModalDateTimePicker = (props) => {

    const { startName, endName, startDate, endDate, setStartDate, setEndDate } = props || {};

    const [selectedStartDate, setSelectedStartDate] = useState(startDate);
    const [selectedEndDate, setSelectedEndDate] = useState(endDate);

    const handleStartDateChange = (date) => {
        setStartDate(date);
        setSelectedStartDate(date);
    };

    const handleEndDateChange = (date) => {
        setEndDate(date);
        setSelectedEndDate(date);
    };

    return (
        <Row className='dates'>
            <Col lg='2'>
                {modal.DATES}
            </Col>
            <Col lg='8'>
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
            </Col>
        </Row>
    );
}

export default RepoModalDateTimePicker;