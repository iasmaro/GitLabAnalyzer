import { ToggleButton, ButtonGroup } from 'react-bootstrap';
import Chart from "react-google-charts";

import './Graph.css';

const Graph = (props) => {
    const { data, radios, radioValue, handleAxisChange, title } = props || {};  

    const options = {
        title,
        titleFontSize: 30,
        chartArea: { width: '90%', height: '50%' },
        hAxis: {
            textPosition: 'out',
            slantedText: true,
            slantedTextAngle: 90,
            textStyle:{color: '#FFF'}

        },
        vAxis: {
            minValue: 0,
            maxValue: 1,
            textStyle:{color: '#FFF'}
        },
        theme: 'material',
        legend: "none",
        backgroundColor:'#4C4975',
        legendTextStyle: { color: '#FFF' },
        titleTextStyle: { color: '#FFF' },
        tooltip: {isHtml: true}
    };

    if (!data) {
        return null;
    }

    return (
        <div className="graph">
            {radios && radios.length && handleAxisChange && <ButtonGroup toggle className="graph-switch">
                {radios.map((radio, idx) => (
                    <ToggleButton
                        key={idx}
                        type="radio"
                        variant="secondary"
                        name="radio"
                        value={radio.value}
                        checked={radioValue === radio.value}
                        onChange={handleAxisChange}
                    >
                        {radio.name}
                    </ToggleButton>
                ))}
            </ButtonGroup>}
            <Chart
                chartType="ColumnChart"
                width="100%"
                height="22em"
                data={data}
                options={options} />
        </div>
    )
}

export default Graph;