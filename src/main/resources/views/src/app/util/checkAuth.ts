export default function checkAuth() : boolean {
    let isAuth = false;

    if( localStorage.getItem(window.atob('isLogin')) == window.atob('true') && localStorage.getItem(window.atob('authUser')) != null ){
        isAuth = true;
    } 

    return isAuth;
}