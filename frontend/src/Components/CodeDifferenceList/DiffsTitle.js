import React from 'react';
import { Button, Popover, OverlayTrigger } from 'react-bootstrap';

import './CodeDifferenceList.css';

const DiffsTitle = (props) => {
    const { diffsTitle } = props || {};
    const diffsTitleDisplay = diffsTitle?.length > 100 ? diffsTitle?.slice(0, 100) + '...' : diffsTitle?.slice(0, 100);


    const popover = (
        <Popover id="popover">
          <Popover.Content>
            {diffsTitle}
          </Popover.Content>
        </Popover>
      );

    return (
        <OverlayTrigger placement="bottom" overlay={popover}>
            <Button variant="secondary" size="lg" className="diffs-title-button" block>{diffsTitleDisplay}</Button>
        </OverlayTrigger>
    );
};

export default DiffsTitle;
