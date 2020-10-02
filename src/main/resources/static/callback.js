console.log("bla-bla-bla");

const token = window.location.hash.substr(1);
console.log(token);
window.history.pushState('', null, '/thank-you');