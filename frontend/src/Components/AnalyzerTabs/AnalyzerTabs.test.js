import { fireEvent, render } from '@testing-library/react';
import '@testing-library/jest-dom';

import { commits } from 'Mocks/mockCommits';
import { mergerequests } from 'Mocks/mockMergeRequests';

import AnalyzerTabs from './AnalyzerTabs';

describe('src/Components/AnalyzerTabs', () => {
    it('should render AnalyzerTabs', () => {
        const { getByTestId } = render(<AnalyzerTabs commits={commits} mergeRequests={mergerequests} />);
        expect(getByTestId('tabs')).toBeInTheDocument();
    });
    
    it('should go to summary tab by default', () => {
        const { getByTestId, getByText } = render(<AnalyzerTabs commits={commits} mergeRequests={mergerequests} />);
        expect(getByTestId('tabs')).toBeInTheDocument();
        expect(getByText('Summary')).toHaveClass('active');
    });

    it('should select merge request tab', async () => {
        const { getAllByText, getByTestId } = render(<AnalyzerTabs commits={commits} mergeRequests={mergerequests} />);
        const MRTab = getAllByText('Merge Requests')[0];
        expect(MRTab).toBeInTheDocument();
        fireEvent.click(MRTab);
        expect(getByTestId('merge-request-tab')).toHaveClass('active');
    });
    
    it('should select commits request tab', async () => {
        const { getAllByText, getByTestId } = render(<AnalyzerTabs commits={commits} mergeRequests={mergerequests} />);
        const commitsTab = getAllByText('Commits')[0];
        expect(commitsTab).toBeInTheDocument();
        fireEvent.click(commitsTab);
        expect(getByTestId('commits-tab')).toHaveClass('active');
    });
});