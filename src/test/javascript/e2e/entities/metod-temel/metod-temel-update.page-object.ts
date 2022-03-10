import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class MetodTemelUpdatePage {
  pageTitle: ElementFinder = element(by.id('servisTakipApp.metodTemel.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  metodAdiInput: ElementFinder = element(by.css('input#metod-temel-metodAdi'));
  metodNuInput: ElementFinder = element(by.css('input#metod-temel-metodNu'));
  metodAciklamasiInput: ElementFinder = element(by.css('input#metod-temel-metodAciklamasi'));
  servisSelect: ElementFinder = element(by.css('select#metod-temel-servis'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setMetodAdiInput(metodAdi) {
    await this.metodAdiInput.sendKeys(metodAdi);
  }

  async getMetodAdiInput() {
    return this.metodAdiInput.getAttribute('value');
  }

  async setMetodNuInput(metodNu) {
    await this.metodNuInput.sendKeys(metodNu);
  }

  async getMetodNuInput() {
    return this.metodNuInput.getAttribute('value');
  }

  async setMetodAciklamasiInput(metodAciklamasi) {
    await this.metodAciklamasiInput.sendKeys(metodAciklamasi);
  }

  async getMetodAciklamasiInput() {
    return this.metodAciklamasiInput.getAttribute('value');
  }

  async servisSelectLastOption() {
    await this.servisSelect.all(by.tagName('option')).last().click();
  }

  async servisSelectOption(option) {
    await this.servisSelect.sendKeys(option);
  }

  getServisSelect() {
    return this.servisSelect;
  }

  async getServisSelectedOption() {
    return this.servisSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.setMetodAdiInput('metodAdi');
    await waitUntilDisplayed(this.saveButton);
    await this.setMetodNuInput('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setMetodAciklamasiInput('metodAciklamasi');
    await this.servisSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
