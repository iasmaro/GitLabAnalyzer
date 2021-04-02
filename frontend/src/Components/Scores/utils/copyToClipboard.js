const copyToClipBoard = (text) => {
    if (navigator.clipboard) {
        navigator.clipboard.writeText(text);
    } else {
        let textArea = document.createElement("textarea");
        textArea.value = text;
        textArea.style.position = 'absolute';
        textArea.style.opacity = 0;
        document.body.appendChild(textArea);
        textArea.select();
        document.execCommand('copy');
        textArea.remove();
    }
}

export default copyToClipBoard;