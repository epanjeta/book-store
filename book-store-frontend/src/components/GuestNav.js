import React, { useState } from 'react';
import { Menu, Icon, Dropdown, DropdownMenu, DropdownItem } from 'semantic-ui-react';
import { useNavigate } from "react-router-dom";

import { useStore } from 'components/Login/StoreContext';

const GuestNav = () => {
    const [state, setState] = useState({ activeItem: "" });
    const navigate = useNavigate();
    const { setUser } = useStore();

    const handleItemClick = (e, { name }) => {
        setState({ activeItem: name });
        navigate(name);
    };

    const handleLogin = async (e, { name }) => {
        navigate("/login");
    };

    return (
        <Menu pointing secondary>
            <Menu.Item name='books' onClick={handleItemClick} style={{ fontSize: '1.5em' }}>
                <span style={{ fontWeight: 'bold' }}>BookShop</span>
            </Menu.Item>
            <Menu.Menu position='right'>
                <Menu.Item name='login' onClick={handleLogin}>
                    <Icon name='user' size='large' color='grey' />
                </Menu.Item>
            </Menu.Menu>
        </Menu>
    );
};

export default GuestNav;