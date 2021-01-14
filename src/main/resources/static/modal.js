// https://bulma.io/lib/main.js?v=202101141737

function getAll(selector) {
    var parent = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : document;

    return Array.prototype.slice.call(parent.querySelectorAll(selector), 0);
}

// Modals

var rootEl = document.documentElement;
var $modals = getAll('.modal');
var $modalButtons = getAll('.modal-button');

var $modalCloses = getAll('.modal-background, .modal-close, .modal-card-head .delete, .modal-card-foot .button');

if ($modalButtons.length > 0) {
    $modalButtons.forEach(function ($el) {
        $el.addEventListener('click', function () {
            let target = $el.dataset.target;

            const imageLink = $($el).attr("imageLink");

            openModal(target, imageLink);
        });
    });
}

if ($modalCloses.length > 0) {
    $modalCloses.forEach(function ($el) {
        $el.addEventListener('click', function () {
            closeModals();
        });
    });
}

function openModal(target, imageLink) {
    let $target = document.getElementById(target);

    $("#image-view-link").attr("src", imageLink)

    rootEl.classList.add('is-clipped');
    $target.classList.add('is-active');
}

function closeModals() {
    rootEl.classList.remove('is-clipped');
    $modals.forEach(function ($el) {
        $el.classList.remove('is-active');
    });
}

document.addEventListener('keydown', function (event) {
    var e = event || window.event;
    if (e.keyCode === 27) {
        closeModals();
        closeDropdowns();
    }
});