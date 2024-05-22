import { StoreProvider } from 'components/Login/StoreContext';
import Root from 'components/Root';
import React from 'react';


const App = () => {
    return (
        <StoreProvider>
            <Root/>
        </StoreProvider>
    );
}

export default App;

