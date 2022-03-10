import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class ServisProjeTemelUpdatePage {
  pageTitle: ElementFinder = element(by.id('servisTakipApp.servisProjeTemel.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  kullanildigiYerInput: ElementFinder = element(by.css('input#servis-proje-temel-kullanildigiYer'));
  servisSelect: ElementFinder = element(by.css('select#servis-proje-temel-servis'));
  projeSelect: ElementFinder = element(by.css('select#servis-proje-temel-proje'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setKullanildigiYerInput(kullanildigiYer) {
    await this.kullanildigiYerInput.sendKeys(kullanildigiYer);
  }

  async getKullanildigiYerInput() {
    return this.kullanildigiYerInput.getAttribute('value');
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

  async projeSelectLastOption() {
    await this.projeSelect.all(by.tagName('option')).last().click();
  }

  async projeSelectOption(option) {
    await this.projeSelect.sendKeys(option);
  }

  getProjeSelect() {
    return this.projeSelect;
  }

  async getProjeSelectedOption() {
    return this.projeSelect.element(by.css('option:checked')).getText();
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
    await this.setKullanildigiYerInput('kullanildigiYer');
    await this.servisSelectLastOption();
    await this.projeSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
