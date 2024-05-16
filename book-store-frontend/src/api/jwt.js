export const getToken = () => {
    const cookies = document.cookie.split(';');
    let token = null;
    for (let i = 0; i < cookies.length; i++) {
      if (cookies[i].trim().startsWith('Bearer')) { // Added trim() to handle spaces
        console.log(cookies[i]);
        token = cookies[i].split('=')[1];
        break;
      }
    }
    return token;
  };