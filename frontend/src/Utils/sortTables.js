import { useState, useMemo } from 'react';

//Adapted from: https://www.smashingmagazine.com/2020/03/sortable-tables-react/
export const useSortableDataObject = (items, config = null) => {
    const [sortConfig, setSortConfig] = useState(config);
    
    const sortedItems = useMemo(() => {
        let sortableItems = items && [...items];
        if (sortConfig !== null) {
            sortableItems.sort((a, b) => {
                if (a[sortConfig.key] < b[sortConfig.key]) {
                    return sortConfig.direction === 'ascending' ? -1 : 1;
                }
                if (a[sortConfig.key] > b[sortConfig.key]) {
                    return sortConfig.direction === 'ascending' ? 1 : -1;
                }
                return 0;
            });
        }
        return sortableItems;
    }, [items, sortConfig]);
    
    const requestSortObject = key => {
        let direction = 'ascending';
        if (sortConfig && sortConfig.key === key && sortConfig.direction === 'ascending') {
            direction = 'descending';
        }
        setSortConfig({ key, direction });
    }
    
    return { items: sortedItems, requestSortObject, sortConfig };
}

export const useSortableDataArray = (items, config = null) => {
    const [sortConfig, setSortConfig] = useState(config);
    
    const sortedItems = useMemo(() => {
        let sortableItems = [...items];
        if (sortConfig !== null) {
            sortableItems.sort((a, b) => {
                if (a < b) {
                    return sortConfig === 'ascending' ? -1 : 1;
                }
                if (a > b) {
                    return sortConfig === 'ascending' ? 1 : -1;
                }
                return 0;
            });
        }
        return sortableItems;
    }, [items, sortConfig]);
    
    const requestSortArray = () => {
        let direction = 'ascending';
        if (sortConfig && sortConfig === 'ascending') {
            direction = 'descending';
        }
        setSortConfig(direction);
    }
    
    return { items: sortedItems, requestSortArray, sortConfig };
}

export const getClassNamesFor = (sortConfig, name = null,) => {
    if (name){
        if (!sortConfig) {
            return 'default';
        }
        return sortConfig.key === name ? sortConfig.direction : 'default';
    }
    else {
        if (!sortConfig) {
            return 'default';
        }
        return sortConfig;
    }
};
