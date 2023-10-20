export const saveTokenToCookies = (token: string) => {
  const expires = new Date();
  expires.setDate(expires.getDate() + 30);
  document.cookie = `token=${token};expires=${expires.toUTCString()};path=/;`;
};

export const saveTokenToSession = (token: string) => {
  sessionStorage.setItem("token", token);
};

export const getTokenFromCookies = () => {
  const cookies = document.cookie.split(";");
  for (let i = 0; i < cookies.length; i++) {
    const cookie = cookies[i].trim();
    if (cookie.startsWith("token=")) {
      return cookie.substring(6);
    }
  }
  return "";
};

export const getTokenFromSession = () => {
  return sessionStorage.getItem("token");
};

export const getToken = () => {
  return getTokenFromCookies() || getTokenFromSession();
};

export const decodeToken = (token: string) => {
  const base64Url = token.split(".")[1];
  const base64 = base64Url.replace("-", "+").replace("_", "/");
  return JSON.parse(window.atob(base64));
};

export const removeToken = () => {
  document.cookie = "token=;expires=Thu, 01 Jan 1970 00:00:01 GMT;path=/;";
  sessionStorage.removeItem("token");
};
